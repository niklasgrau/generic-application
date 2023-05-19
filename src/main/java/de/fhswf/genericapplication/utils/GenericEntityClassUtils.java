package de.fhswf.genericapplication.utils;

import de.fhswf.genericapplication.annotations.InverseAssociation;
import de.fhswf.genericapplication.fieldfilters.GenericEntityAssociationFieldFilter;
import de.fhswf.genericapplication.fieldfilters.GenericEntityDefaultFieldFilter;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Util class for help functions to access generic entity parts via reflections
 *
 * @author Niklas Grau
 */
@Service
public class GenericEntityClassUtils {

    /**
     * Creates a map of attributes of the given object by reflections.
     *
     * @param object to generate a filtered attribute map
     * @param filter
     * @return
     */
    public Map<String, Object> attributesAsFilteredMap(Object object, ReflectionUtils.FieldFilter filter) {
        Map<String, Object> properties = new HashMap<>();

        ReflectionUtils.doWithFields(object.getClass(), field -> {
            ReflectionUtils.makeAccessible(field);

            if (field.get(object) != null) {
                properties.put(field.getName(), field.get(object));
            }
        }, filter);

        return properties;
    }

    /**
     * Creates a map of the objects attributes.
     *
     * @param object to create an attribute map from
     * @return
     */
    public Map<String, Object> entityAttributesAsMap(Object object) {
        return this.attributesAsFilteredMap(object, new GenericEntityDefaultFieldFilter());
    }

    /**
     * Creates a map of the objects association attributes.
     *
     * @param object to create an association attribute map from
     * @return
     */
    public Map<String, Object> entityAssociationAttributesAsMap(Object object) {
        return this.attributesAsFilteredMap(object, new GenericEntityAssociationFieldFilter());
    }

    /**
     * Checks if a given property name exists on the object
     *
     * @param object which should contain the property
     * @param name   property name to check
     * @return
     */
    public boolean isProperty(Object object, String name) {
        PropertyAccessor propertyAccessor = PropertyAccessorFactory.forBeanPropertyAccess(object);
        return propertyAccessor.isReadableProperty(name);
    }

    /**
     * Checks if a given property and annotation exists on the object
     *
     * @param object         which should contain the annotated property
     * @param name           property name of the annotation
     * @param annotationType annotation type to check
     * @return
     */
    public boolean hasAnnotation(Object object, String name, Class<? extends Annotation> annotationType) {
        PropertyAccessor propertyAccessor = PropertyAccessorFactory.forBeanPropertyAccess(object);
        return Objects.requireNonNull(propertyAccessor.getPropertyTypeDescriptor(name)).hasAnnotation(annotationType);
    }

    /**
     * Gets the property of an object.
     *
     * @param object to get the property from
     * @param name   property name to determine from the given object
     * @return
     */
    public Object getProperty(Object object, String name) {
        PropertyAccessor propertyAccessor = PropertyAccessorFactory.forBeanPropertyAccess(object);
        return propertyAccessor.getPropertyValue(name);
    }

    /**
     * Sets the property of an object.
     *
     * @param object to set the property on
     * @param name   property name to set the value
     * @param value  the value to set on the property
     */
    public void setProperty(Object object, String name, Object value) {
        PropertyAccessor propertyAccessor = PropertyAccessorFactory.forBeanPropertyAccess(object);
        propertyAccessor.setPropertyValue(name, value);
    }

    /**
     * Checks if the objects property is a many-to-many association.
     *
     * @param object which should contain the property type
     * @param name   property name of the association attribute
     * @return
     */
    public boolean isManyToManyProperty(Object object, String name) {
        PropertyAccessor propertyAccessor = PropertyAccessorFactory.forBeanPropertyAccess(object);
        return Objects.requireNonNull(propertyAccessor.getPropertyTypeDescriptor(name)).hasAnnotation(ManyToMany.class);
    }

    /**
     * Checks if the objects property is a defining many-to-many-association.
     * @param object which should contain th property type
     * @param name   property name of the association attribute
     * @return
     */
    public boolean isDefiningManyToManyProperty(Object object, String name) {
        return isManyToManyProperty(object, name) && hasAnnotation(object, name, JoinTable.class);
    }

    /**
     * Reads the inverse association property name of the given association property.
     *
     * @param object to read the property value from
     * @param name   the property name annotated by an {@link InverseAssociation}
     * @return
     */
    public String getAssociationPropertyName(Object object, String name) {
        PropertyAccessor propertyAccessor = PropertyAccessorFactory.forBeanPropertyAccess(object);
        return propertyAccessor.getPropertyTypeDescriptor(name).getAnnotation(InverseAssociation.class).inverseRelationName();
    }

    /**
     * Checks if the objects property is a {@link Collection}
     *
     * @param object which should contain the property type
     * @param name   property name of the collection attribute
     * @return
     */
    public boolean isCollectionProperty(Object object, String name) {
        PropertyAccessor propertyAccessor = PropertyAccessorFactory.forBeanPropertyAccess(object);
        return Collection.class.isAssignableFrom(Objects.requireNonNull(propertyAccessor.getPropertyType(name)));
    }

    /**
     * Adds a value to a collection property of the given object.
     *
     * @param object which contains a collection property
     * @param name   the property name of the collection attribute
     * @param value  the value to add to the collection
     */
    public void addToCollectionProperty(Object object, String name, Object value) {
        PropertyAccessor propertyAccessor = PropertyAccessorFactory.forBeanPropertyAccess(object);
        Collection<Object> collectionProperty = (Collection<Object>) propertyAccessor.getPropertyValue(name);
        collectionProperty.add(value);
    }

    /**
     * Removes a value from a collection property of the given object.
     *
     * @param object which contains a collection property
     * @param name   the property name of the collection attribute
     * @param value  the value to remove from the collection
     */
    public void removeFromCollectionProperty(Object object, String name, Object value) {
        PropertyAccessor propertyAccessor = PropertyAccessorFactory.forBeanPropertyAccess(object);
        Collection<Object> collectionProperty = (Collection<Object>) propertyAccessor.getPropertyValue(name);
        collectionProperty.remove(value);
    }
}
