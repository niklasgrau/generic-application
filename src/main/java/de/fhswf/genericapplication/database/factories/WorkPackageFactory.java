package de.fhswf.genericapplication.database.factories;

import de.fhswf.genericapplication.models.WorkPackage;
import de.fhswf.genericapplication.models.WorkPackageTypeET;
import org.springframework.stereotype.Component;

/**
 * @author Niklas Grau
 */
@Component
public class WorkPackageFactory extends BaseFactory<WorkPackage> {
    @Override
    public WorkPackage create() {
        return new WorkPackage(
                this.faker.lorem().sentence(),
                this.faker.lorem().paragraph(2),
                String.format("%s-%s", this.faker.number().digits(5), this.faker.number().digits(2)),
                this.faker.bool().bool(),
                this.faker.options().option(WorkPackageTypeET.class),
                null
        );
    }
}
