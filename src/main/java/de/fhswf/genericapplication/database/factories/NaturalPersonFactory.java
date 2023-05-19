package de.fhswf.genericapplication.database.factories;

import de.fhswf.genericapplication.models.NaturalPerson;
import org.springframework.stereotype.Component;

/**
 * @author Niklas Grau
 */
@Component
public class NaturalPersonFactory extends BaseFactory<NaturalPerson> {
    @Override
    public NaturalPerson create() {
        return new NaturalPerson(
                this.faker.name().lastName(),
                this.faker.name().firstName()
        );
    }
}
