package de.fhswf.genericapplication.database.seeders;

import de.fhswf.genericapplication.database.factories.OrderFactory;
import de.fhswf.genericapplication.models.Offer;
import de.fhswf.genericapplication.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Niklas Grau
 */
@Component
public class OrderSeeder extends BaseSeeder {
    @Autowired
    private OrderFactory orderFactory;

    @Override
    public void seed() throws Exception {
        List<Order> orders = this.orderFactory.create(10);

        Page<Offer> offers = this.repository.findAll(Offer.class, PageRequest.of(0, 15), null);
        orders.forEach(order -> {
            order.setOffer(this.faker.options().nextElement(offers.getContent()));
        });

        this.repository.createAll(orders);
    }

    @Override
    public int getSeedingOrder() {
        return 2;
    }
}
