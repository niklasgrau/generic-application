package de.fhswf.genericapplication.database.seeders;

import de.fhswf.genericapplication.database.factories.WorkPackageFactory;
import de.fhswf.genericapplication.models.Project;
import de.fhswf.genericapplication.models.WorkPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Niklas Grau
 */
@Component
public class WorkPackageSeeder extends BaseSeeder {
    @Autowired
    WorkPackageFactory workPackageFactory;

    @Override
    public void seed() throws Exception {
        List<WorkPackage> workPackages = this.workPackageFactory.create(15);

        Page<Project> projects = this.repository.findAll(Project.class, PageRequest.of(0, 15), null);
        workPackages.forEach(workPackage -> {
            workPackage.setProject(this.faker.options().nextElement(projects.getContent()));
        });

        this.repository.createAll(workPackages);
    }

    @Override
    public int getSeedingOrder() {
        return 4;
    }
}
