package de.fhswf.genericapplication.database.seeders;

/**
 * @author Niklas Grau
 */
public interface Seeder {
    /**
     * Runs the seeding process.
     */
    void seed() throws Exception;

    /**
     * Gets the seeding order prioritisation, the lower the number the higher the prioritisation.
     *
     * @return int
     */
    int getSeedingOrder();
}
