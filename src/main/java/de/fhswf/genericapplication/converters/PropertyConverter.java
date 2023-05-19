package de.fhswf.genericapplication.converters;

import de.fhswf.genericapplication.models.BaseEntity;

/**
 * Interface to implement a {@link PropertyConverter}, which converts a given property from a given source type to a declared target type.
 *
 * @param <SOURCE> Source class type to convert from.
 * @param <TARGET> Target class type to convert to.
 * @author Kevin Link
 */
public interface PropertyConverter<SOURCE, TARGET> {

    /**
     * Converts the given source value of the property ({@code key}), from the given {@link BaseEntity}.
     *
     * @param value  Source value of the property (in {@link SOURCE} format)
     * @param key    Property key/field name of the {@link BaseEntity}
     * @param entity any {@link BaseEntity}
     * @return Converted result in {@link TARGET} format.
     */
    TARGET convert(SOURCE value, String key, BaseEntity entity);

    /**
     * Returns the class of the {@link SOURCE} to determine class this converter is able to convert.
     *
     * @return Supported class to be converted.
     */
    Class<SOURCE> supports();
}
