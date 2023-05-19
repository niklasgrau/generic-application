package de.fhswf.genericapplication.database.factories;

import de.fhswf.genericapplication.models.JuristicPerson;
import org.springframework.stereotype.Component;

/**
 * @author Niklas Grau
 */
@Component
public class JuristicPersonFactory extends BaseFactory<JuristicPerson> {
    @Override
    public JuristicPerson create() {
        return new JuristicPerson(
                this.faker.company().name(),
                String.format(
                        "%s/%s/%s",
                        this.faker.number().digits(3),
                        this.faker.number().digits(3),
                        this.faker.number().digits(5)
                )
        );
    }
}
