package de.fhswf.genericapplication.database.factories;

import de.fhswf.genericapplication.models.Test;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author Niklas Grau
 */
@Component
public class TestFactory extends BaseFactory<Test> {
    @Override
    public Test create() {
        return new Test(
                this.faker.bool().bool(),
                this.faker.bool().bool(),
                (byte) this.faker.number().randomDigit(),
                (byte) this.faker.number().randomDigit(),
                (short) this.faker.number().randomDigit(),
                (short) this.faker.number().randomDigit(),
                this.faker.number().randomDigit(),
                this.faker.number().randomDigit(),
                this.faker.number().randomNumber(),
                this.faker.number().randomNumber(),
                Float.parseFloat(this.faker.number().randomDigit() + "." + this.faker.number().randomDigit()),
                Float.parseFloat(this.faker.number().randomDigit() + "." + this.faker.number().randomDigit()),
                this.faker.number().randomDouble(2, 0, 100000),
                this.faker.number().randomDouble(2, 0, 100000),
                new BigDecimal(this.faker.number().randomNumber()),
                this.faker.lorem().sentence(),
                this.faker.lorem().character(),
                this.faker.lorem().character(),
                this.formatDate(this.faker.date().birthday())
        );
    }

    public Test createNullabled() {
        return new Test(
                this.faker.bool().bool(),
                null,
                (byte) this.faker.number().randomDigit(),
                null,
                (short) this.faker.number().randomDigit(),
                null,
                this.faker.number().randomDigit(),
                null,
                this.faker.number().randomNumber(),
                null,
                Float.parseFloat(this.faker.number().randomDigit() + "." + this.faker.number().randomDigit()),
                null,
                this.faker.number().randomDouble(2, 0, 100000),
                null,
                null,
                null,
                this.faker.lorem().character(),
                null,
                null
        );
    }
}
