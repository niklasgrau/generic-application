package de.fhswf.genericapplication.repositories;

import de.fhswf.genericapplication.annotations.InverseAssociation;
import de.fhswf.genericapplication.dto.requests.FilterBlockOperatorET;
import de.fhswf.genericapplication.dto.requests.FilterMember;
import de.fhswf.genericapplication.dto.requests.FilterOperatorET;
import de.fhswf.genericapplication.dto.requests.FilterStatement;
import de.fhswf.genericapplication.filter.data.DataTypeET;
import de.fhswf.genericapplication.filter.data.FilterOperatorGroupFactory;
import de.fhswf.genericapplication.filter.data.FilterPair;
import de.fhswf.genericapplication.filter.predicates.FilterPredicate;
import de.fhswf.genericapplication.models.BaseEntity;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.ReflectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.ManyToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Default implementation of the {@link GenericEntityRepository}. CRUD operations on a {@link BaseEntity} are
 * handled by the {@link EntityManager}.
 *
 * @author Niklas Grau, Kevin Link
 */
@Repository
public class DefaultGenericEntityRepository implements GenericEntityRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private Map<DataTypeET, FilterPredicate> filterPredicateMap;

    @Override
    public <E extends BaseEntity> E create(E entity) {
        this.entityManager.persist(entity);
        return entity;
    }

    @Override
    public <E extends BaseEntity> List<E> createAll(Iterable<E> entities) {
        List<E> result = new ArrayList<>();
        for (E entity : entities) {
            result.add(this.create(entity));
        }

        return result;
    }

    @Override
    public <E extends BaseEntity> E findById(Class<E> entityClass, Long id) {
        return this.entityManager.find(entityClass, id);
    }

    @Override
    public <E extends BaseEntity> Page<E> findAll(
            Class<E> entityClass, Pageable pagination, FilterStatement filter
    ) {
        CriteriaBuilder entityBuilder = this.entityManager.getCriteriaBuilder();
        List<E> resultList = this.queryEntities(entityBuilder, entityClass, pagination, filter);

        CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
        Long totalRows = this.queryTotalRows(queryBuilder, entityBuilder, entityClass, filter);

        return new PageImpl<>(resultList, pagination, totalRows);
    }

    @Override
    public <E extends BaseEntity> E update(E entity) {
        this.entityManager.merge(entity);
        return entity;
    }

    @Override
    public <E extends BaseEntity> List<E> updateAll(Iterable<E> entities) {
        List<E> result = new ArrayList<>();
        for (E entity : entities) {
            result.add(this.update(entity));
        }

        return result;
    }

    @Override
    public <E extends BaseEntity> void delete(E entity) {
        this.entityManager.remove(entity);
    }

    @Override
    public <E extends BaseEntity> void deleteById(Class<E> entityClass, Long id) {
        E entity = this.entityManager.find(entityClass, id);
        this.entityManager.remove(entity);
    }

    private <E extends BaseEntity> List<E> queryEntities(
            CriteriaBuilder builder, Class<E> entityClass, Pageable pagination, FilterStatement filter
    ) {
        CriteriaQuery<E> query = builder.createQuery(entityClass);
        Root<E> entityRoot = query.from(entityClass);
        query.select(entityRoot);

        Optional<Predicate> queryFilters = this.getFilterPredicate(builder, entityRoot, entityClass, filter);
        queryFilters.ifPresent(query::where);
        query.orderBy(QueryUtils.toOrders(pagination.getSort(), entityRoot, builder));
        query.distinct(true);

        TypedQuery<E> typedQuery = this.entityManager.createQuery(query);
        typedQuery.setFirstResult(pagination.getPageNumber() * pagination.getPageSize());
        typedQuery.setMaxResults(pagination.getPageSize());
        return typedQuery.getResultList();
    }

    private <E extends BaseEntity> Long queryTotalRows(
            CriteriaBuilder builder, CriteriaBuilder entityBuilder, Class<E> entityClass, FilterStatement filter
    ) {
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<E> root = query.from(entityClass);
        query.select(builder.count(root));

        Optional<Predicate> queryFilter = this.getFilterPredicate(entityBuilder, root, entityClass, filter);
        queryFilter.ifPresent(query::where);
        return entityManager.createQuery(query).getSingleResult();
    }

    private <E extends BaseEntity> Optional<Predicate> getFilterPredicate(
            CriteriaBuilder builder, Root<E> root, Class<E> entityClass, FilterStatement filter
    ) {
        if (filter == null || filter.getBlocks().isEmpty()) {
            return Optional.empty();
        }

        List<FilterPair> blockPredicates = new ArrayList<>();

        // Iterate over all filter blocks
        filter.getBlocks().forEach(filterBlock -> {
            List<Predicate> blockGroupedPredicates = new ArrayList<>();

            // Iterate over all filter statements of each filter block
            filterBlock.getStatements()
                    // Ignore empty given filters
                    .stream().filter(filterMember ->
                            filterMember.getMemberName() != null && filterMember.getOperator() != null
                    )
                    .forEach(filterMember -> {
                        // Identify the attribute from the entity
                        Field entityField = Objects.requireNonNull(
                                ReflectionUtils.findField(entityClass, filterMember.getMemberName())
                        );

                        // Validate if the given filter member operator and data type are compatible
                        DataTypeET dataType = this.validateFilterOperatorAndDataType(
                                entityField.getType(), filterMember.getOperator()
                        );

                        // ManyToMany attributes
                        if (entityField.getAnnotation(ManyToMany.class) != null) {
                            InverseAssociation inverse = entityField.getAnnotation(InverseAssociation.class);
                            if (inverse == null) {
                                throw new RuntimeException(
                                        "InverseAssociation not found for field " + entityField.getName() + "."
                                );
                            }

                            blockGroupedPredicates.add(this.getManyToManyPredicate(root, builder, filterMember));
                        } else { // Regular attributes
                            // Identifies the CriteriaBuilder method, like "equal" or "greaterThan"
                            Method method = ReflectionUtils.findMethod(
                                    CriteriaBuilder.class,
                                    filterMember.getOperator().getOperator(),
                                    filterMember.getOperator().getParamTypes()
                            );

                            // In dependence of the filter data type and operator create a list of predicates
                            try {
                                FilterPredicate filterPredicate = filterPredicateMap.get(dataType);
                                if (FilterOperatorET.IN.equals(filterMember.getOperator())) {
                                    filterPredicate = filterPredicateMap.get(DataTypeET.COLLECTION);
                                }
                                blockGroupedPredicates.add(filterPredicate.toPredicate(
                                        root, builder, method, entityField, filterMember
                                ));
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });

            // In case of multiple blocks create a list of elements which
            // defines the block statements and operators within
            if (!blockGroupedPredicates.isEmpty()) {
                Predicate blockPredicate = builder.and(blockGroupedPredicates.toArray(Predicate[]::new));
                blockPredicates.add(new FilterPair(filterBlock.getOperator(), blockPredicate));
            }
        });

        // Concat blocks by desired operator
        Predicate lastPredicate = null;
        for (FilterPair blockPredicate : blockPredicates) {
            if (lastPredicate == null) {
                lastPredicate = builder.and(blockPredicate.getPredicate());
            } else if (FilterBlockOperatorET.AND.equals(blockPredicate.getOperator())) {
                lastPredicate = builder.and(lastPredicate, blockPredicate.getPredicate());
            } else if (FilterBlockOperatorET.OR.equals(blockPredicate.getOperator())) {
                lastPredicate = builder.or(lastPredicate, blockPredicate.getPredicate());
            }
        }

        return Optional.ofNullable(lastPredicate);
    }

    private DataTypeET validateFilterOperatorAndDataType(
            Class<?> memberFieldType, FilterOperatorET filterOperator
    ) {
        Optional<DataTypeET> dataTypeOptional = Arrays.stream(DataTypeET.values()).filter(dataTypeET -> {
            if (memberFieldType.isPrimitive()) {
                return dataTypeET.getClazz().equals(ClassUtils.primitiveToWrapper(memberFieldType));
            }

            return dataTypeET.getClazz().equals(memberFieldType) || dataTypeET.getClazz().isAssignableFrom(memberFieldType);
        }).findFirst();

        DataTypeET datatype = dataTypeOptional.orElseThrow(() -> new IllegalArgumentException(String.format("DataType %s for filter operator %s not compatible", memberFieldType, filterOperator)));
        if (!List.of(FilterOperatorGroupFactory.createOperatorGroup(datatype)).contains(filterOperator)) {
            throw new RuntimeException("DataType does not match the requested filter operator.");
        }

        return datatype;
    }

    private <E extends BaseEntity> Predicate getManyToManyPredicate(Root<E> root, CriteriaBuilder builder, FilterMember filterMember) {
        // Join on M:N and N
        Join<E, E> join = root.join(filterMember.getMemberName(), JoinType.LEFT);
        return builder.equal(join.get("id"), filterMember.getValue());
    }
}
