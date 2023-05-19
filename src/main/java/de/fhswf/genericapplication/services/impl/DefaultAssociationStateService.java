package de.fhswf.genericapplication.services.impl;

import de.fhswf.genericapplication.annotations.InverseAssociation;
import de.fhswf.genericapplication.exceptions.ModelConstraintException;
import de.fhswf.genericapplication.exceptions.ModelNotFoundException;
import de.fhswf.genericapplication.models.BaseEntity;
import de.fhswf.genericapplication.repositories.GenericEntityRepository;
import de.fhswf.genericapplication.services.AssociationStateService;
import de.fhswf.genericapplication.services.BaseEntityService;
import de.fhswf.genericapplication.utils.GenericEntityClassUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Default implementation of the {@link AssociationStateService}. The service handles association states of entities by
 * connecting, disconnecting or deleting the associations. The association will be persisted by generically determination
 * of the entity's association property by {@link InverseAssociation}.
 *
 * @author Niklas Grau
 */
@Service
public class DefaultAssociationStateService implements AssociationStateService {
    @Autowired
    GenericEntityRepository genericEntityRepository;

    @Autowired
    GenericEntityClassUtils genericEntityClassUtils;

    @Autowired
    BaseEntityService baseEntityService;

    @Override
    public BaseEntity connect(Long associationTypeId, Long associationId, String propertyName, BaseEntity origin) {
        boolean isDefiningManyToManyProperty = this.genericEntityClassUtils.isDefiningManyToManyProperty(origin, propertyName);
        BaseEntity association = baseEntityService.getBaseEntity(associationTypeId, associationId);

        // In case of ManyToMany the association update has to be made on the defining side to have an effect in Spring
        if (isDefiningManyToManyProperty) {
            return this.connect(origin, propertyName, association);
        } else { // Otherwise, the inverse property name of the association name has to be used
            String inversePropertyName = this.genericEntityClassUtils.getAssociationPropertyName(origin, propertyName);
            return this.connect(association, inversePropertyName, origin);
        }
    }

    /**
     * Connects an entity to another one.
     * Since associations can be to-one or to-many, this function determines the property type and updates it properly.
     *
     * @param entity       the entity to connect another entity to.
     * @param propertyName name of the association property.
     * @param association  the entity to associate.
     * @return the connected {@link BaseEntity}.
     */
    @Override
    public BaseEntity connect(BaseEntity entity, String propertyName, BaseEntity association) {
        if (this.isToManyAssociation(entity, propertyName)) {
            this.genericEntityClassUtils.addToCollectionProperty(entity, propertyName, association);
        } else { // to-one association
            this.genericEntityClassUtils.setProperty(entity, propertyName, association);
        }

        return this.genericEntityRepository.update(entity);
    }

    @Override
    public BaseEntity disconnect(Long associationTypeId, Long associationId, String propertyName, BaseEntity origin) {
        boolean isDefiningManyToManyProperty = this.genericEntityClassUtils.isDefiningManyToManyProperty(origin, propertyName);
        BaseEntity association = baseEntityService.getBaseEntity(associationTypeId, associationId);

        // In case of ManyToMany the association update has to be made on the defining side to have an effect in Spring
        if (isDefiningManyToManyProperty) {
            return this.disconnect(origin, propertyName, association);
        } else { // Otherwise, the inverse property name of the association name has to be used
            String inversePropertyName = this.genericEntityClassUtils.getAssociationPropertyName(origin, propertyName);
            return this.disconnect(association, inversePropertyName, origin);
        }
    }

    @Override
    public BaseEntity disconnect(BaseEntity entity, String propertyName, BaseEntity association) {
        if (this.isToManyAssociation(entity, propertyName)) {
            this.genericEntityClassUtils.removeFromCollectionProperty(entity, propertyName, association);
        } else { // to-one association
            this.genericEntityClassUtils.setProperty(entity, propertyName, null);
        }

        return this.genericEntityRepository.update(entity);
    }

    @Override
    public void delete(Long typeId, Long id) {
        try {
            Class<? extends BaseEntity> associationClass = this.baseEntityService.getClassByTypeId(typeId);
            this.genericEntityRepository.deleteById(associationClass, id);
        } catch (DataIntegrityViolationException e) {
            String constraintName = ((ConstraintViolationException) e.getCause()).getConstraintName();
            Pattern pattern = Pattern.compile("PUBLIC\\.(.*) FOREIGN\\.*", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(constraintName);

            if (matcher.find()) {
                throw new ModelConstraintException(this.baseEntityService.getBaseEntity(typeId, id), matcher.group(1), e);
            }

            throw e;
        } catch (Throwable th) {
            throw new ModelNotFoundException(typeId, id, th);
        }
    }

    @Override
    public void delete(BaseEntity entity) {
        this.genericEntityRepository.delete(entity);
    }

    /**
     * Checks whether the given entities property is a toMany association or not.
     *
     * @param entity       the {@link BaseEntity}.
     * @param propertyName name of the entity's property.
     * @return the boolean value determining the property as toMany or not.
     */
    private boolean isToManyAssociation(BaseEntity entity, String propertyName) {
        return this.genericEntityClassUtils.isCollectionProperty(entity, propertyName);
    }
}
