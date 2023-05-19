package de.fhswf.genericapplication.database.factories;

import de.fhswf.genericapplication.models.User;
import org.springframework.stereotype.Component;

/**
 * @author Niklas Grau
 */
@Component
public class UserFactory extends BaseFactory<User> {
    @Override
    public User create() {
        String firstName = this.faker.name().firstName();
        String lastName = this.faker.name().lastName();

        return new User(
                this.faker.internet().emailAddress(String.format("%s.%s", firstName, lastName)),
                this.faker.internet().password(),
                firstName,
                lastName
        );
    }

    public User createDefaultAdmin() {
        String firstName = "Admin";
        String lastName = "Admin";

        return new User(
                String.format("%s.%s@%s", firstName, lastName, "admin.com"),
                "admin",
                firstName,
                lastName
        );
    }
}
