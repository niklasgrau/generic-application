package de.fhswf.genericapplication.database.seeders;

import de.fhswf.genericapplication.database.factories.ExpenditureFactory;
import de.fhswf.genericapplication.models.Employee;
import de.fhswf.genericapplication.models.Expenditure;
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
public class ExpenditureSeeder extends BaseSeeder {
    @Autowired
    ExpenditureFactory expenditureFactory;

    @Override
    public void seed() throws Exception {
        List<Expenditure> expenditures = this.expenditureFactory.create(15);

        Page<Employee> employees = this.repository.findAll(Employee.class, PageRequest.of(0, 15), null);
        expenditures.forEach(expenditure -> {
            expenditure.setEmployee(this.faker.options().nextElement(employees.getContent()));
        });

        Page<Project> projects = this.repository.findAll(Project.class, PageRequest.of(0, 15), null);
        expenditures.forEach(expenditure -> {
            expenditure.setProject(this.faker.options().nextElement(projects.getContent()));
        });

        Page<WorkPackage> workPackages = this.repository.findAll(WorkPackage.class, PageRequest.of(0, 15), null);
        expenditures.forEach(expenditure -> {
            if (this.faker.bool().bool()) {
                expenditure.setWorkPackage(this.faker.options().nextElement(workPackages.getContent()));
            }
        });

        this.repository.createAll(expenditures);
    }

    @Override
    public int getSeedingOrder() {
        return 5;
    }
}
