package de.fhswf.genericapplication.database.factories;

import java.util.List;

/**
 * Base interface for database factories to create objects of type {@link T}.
 *
 * @author Niklas Grau
 */
public interface Factory<T> {
    /**
     * Creates an instance of type {@link T}.
     *
     * @return an instance of type {@link T}.
     */
    T create();

    /**
     * Creates a list of instances of type {@link T}.
     *
     * @param amount number of instances to create.
     * @return a list of instances of type {@link T} with the length of {@param amount}.
     */
    List<T> create(int amount);
}
