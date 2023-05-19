package de.fhswf.genericapplication.database.seeders;

import de.fhswf.genericapplication.database.factories.EmployeeFactory;
import de.fhswf.genericapplication.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Niklas Grau
 */
@Component
public class EmployeeSeeder extends BaseSeeder {
    @Autowired
    EmployeeFactory employeeFactory;

    @Override
    public void seed() throws Exception {
        List<Employee> employees = this.repository.createAll(this.employeeFactory.create(15));
    }

    @Override
    public int getSeedingOrder() {
        return 0;
    }
}
