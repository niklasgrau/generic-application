package de.fhswf.genericapplication.database.factories;

import de.fhswf.genericapplication.models.Project;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Niklas Grau
 */
@Component
public class ProjectFactory extends BaseFactory<Project> {
    @Override
    public Project create() {
        return new Project(
                this.formatDate(this.faker.date().future(30, 5, TimeUnit.DAYS)),
                this.formatDate(this.faker.date().future(365, 30, TimeUnit.DAYS)),
                this.faker.app().name(),
                String.format(
                        "%s-%s",
                        this.faker.number().digits(3),
                        this.faker.number().digits(2)
                ),
                null,
                null
        );
    }
}
