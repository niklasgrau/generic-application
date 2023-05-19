package de.fhswf.genericapplication.database.factories;

import de.fhswf.genericapplication.models.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Niklas Grau
 */
@Component
public class OrderFactory extends BaseFactory<Order> {
    @Override
    public Order create() {
        return new Order(
                String.format(
                        "%s-%s-%s",
                        this.faker.number().digits(3),
                        this.faker.number().digits(4),
                        this.faker.number().digits(2)
                ),
                this.formatDate(this.faker.date().past(30, TimeUnit.DAYS)),
                null,
                null
        );
    }
}
