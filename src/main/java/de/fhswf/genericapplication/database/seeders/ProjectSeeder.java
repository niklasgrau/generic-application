package de.fhswf.genericapplication.database.seeders;

import de.fhswf.genericapplication.database.factories.ProjectFactory;
import de.fhswf.genericapplication.models.Employee;
import de.fhswf.genericapplication.models.Order;
import de.fhswf.genericapplication.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Niklas Grau
 */
@Component
public class ProjectSeeder extends BaseSeeder {
    @Autowired
    private ProjectFactory projectFactory;

    @Override
    public void seed() throws Exception {
        List<Project> projects = this.projectFactory.create(15);
        Page<Employee> employees = this.repository.findAll(Employee.class, PageRequest.of(0, 15), null);
        Page<Order> orders = this.repository.findAll(Order.class, PageRequest.of(0, 15), null);

        projects.forEach(project -> {
            project.setManager(this.faker.options().nextElement(employees.getContent()));

            if (this.faker.bool().bool()) {
                project.setDeputyManager(this.faker.options().nextElement(employees.getContent()));
            }

            if (this.faker.bool().bool()) {
                Set<Employee> team = new HashSet<>();
                employees.getContent().forEach(employee -> {
                    if (Integer.parseInt(this.faker.number().digits(2)) < 20) {
                        team.add(employee);
                    }
                });

                project.setTeam(team);
            }

            // x % probability of having only one order
            if (Integer.parseInt(this.faker.number().digits(2)) < 80) {
                Order order = this.faker.options().nextElement(orders.getContent());

                order.setProjects(new HashSet<>(List.of(project)));
                project.setOrders(new HashSet<>(List.of(order)));
            } else {
                Set<Order> orderSet = new HashSet<>();

                // Add random amount of orders to the set
                orders.getContent().forEach(order -> {
                    // To high probability could result in having all projects the same order
                    if (Integer.parseInt(this.faker.number().digits(2)) < 20) {
                        order.setProjects(new HashSet<>(List.of(project)));
                        orderSet.add(order);
                    }
                });

                project.setOrders(orderSet);
            }
        });

        this.repository.createAll(projects);
        this.repository.updateAll(orders);
    }

    @Override
    public int getSeedingOrder() {
        return 3;
    }
}
