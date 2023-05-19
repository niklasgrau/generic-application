package de.fhswf.genericapplication.database.factories;

import de.fhswf.genericapplication.models.Employee;
import org.springframework.stereotype.Component;

/**
 * @author Niklas Grau
 */
@Component
public class EmployeeFactory extends BaseFactory<Employee> {
    @Override
    public Employee create() {
        String firstName = this.faker.name().firstName();
        String lastName = this.faker.name().lastName();

        return new Employee(
                this.faker.internet().emailAddress(String.format("%s.%s", firstName, lastName)),
                this.faker.internet().password(),
                firstName,
                lastName
        );
    }
}
