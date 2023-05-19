package de.fhswf.genericapplication.database.factories;

import de.fhswf.genericapplication.models.Expenditure;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Niklas Grau
 */
@Component
public class ExpenditureFactory extends BaseFactory<Expenditure> {
    @Override
    public Expenditure create() {
        return new Expenditure(
                this.formatDate(this.faker.date().past(10, TimeUnit.DAYS)),
                this.formatDate(this.faker.date().future(30, 1, TimeUnit.DAYS)),
                this.faker.lorem().paragraph(),
                this.faker.bool().bool(),
                null,
                null,
                null
        );
    }
}
