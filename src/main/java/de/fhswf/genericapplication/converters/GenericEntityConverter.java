package de.fhswf.genericapplication.converters;

import de.fhswf.genericapplication.dto.GenericEntityDto;
import de.fhswf.genericapplication.exceptions.PropertyNotFoundException;
import de.fhswf.genericapplication.models.BaseEntity;

/**
 * Interface for conversion between {@link BaseEntity} and {@link GenericEntityDto}.
 *
 * @author Niklas Grau
 */
public interface GenericEntityConverter {
    /**
     * Converts a {@link BaseEntity} to {@link GenericEntityDto}.
     *
     * @param source {@link BaseEntity} used for conversion.
     * @return the {@link GenericEntityDto} representation of {@link BaseEntity}.
     */
    GenericEntityDto convert(BaseEntity source);

    /**
     * Converts a {@link GenericEntityDto} to {@link BaseEntity}.
     *
     * @param source {@link GenericEntityDto} used for conversion.
     * @param target {@link BaseEntity} to convert to.
     * @return the {@link BaseEntity} representation of {@link GenericEntityDto}
     * @throws PropertyNotFoundException if a property name does not exist in the target class.
     */
    BaseEntity convert(GenericEntityDto source, BaseEntity target);

    /**
     * Converts a {@link GenericEntityDto} to {@link BaseEntity}.
     *
     * @param source {@link GenericEntityDto} used for conversion.
     * @param target the class type of the targets {@link BaseEntity}
     * @return the {@link BaseEntity} representation of {@link GenericEntityDto}
     * @throws PropertyNotFoundException if a property name does not exist in the target class.
     */
    BaseEntity convert(GenericEntityDto source, Class<? extends BaseEntity> target);
}
