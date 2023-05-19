package de.fhswf.genericapplication.database.seeders;

import com.github.javafaker.Faker;
import de.fhswf.genericapplication.repositories.GenericEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Niklas Grau
 */
public abstract class BaseSeeder implements Seeder {
    @Autowired
    protected Faker faker;

    @Autowired
    protected GenericEntityRepository repository;
}
