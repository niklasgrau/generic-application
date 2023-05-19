package de.fhswf.genericapplication.database.seeders;

import de.fhswf.genericapplication.database.factories.OfferFactory;
import de.fhswf.genericapplication.models.Contact;
import de.fhswf.genericapplication.models.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Niklas Grau
 */
@Component
public class OfferSeeder extends BaseSeeder {
    @Autowired
    private OfferFactory offerFactory;

    @Override
    public void seed() throws Exception {
        List<Offer> offers = this.offerFactory.create(10);

        Page<Contact> contacts = this.repository.findAll(Contact.class, PageRequest.of(0, 15), null);
        offers.forEach(offer -> {
            offer.setContact(this.faker.options().nextElement(contacts.getContent()));
        });

        this.repository.createAll(offers);
    }

    @Override
    public int getSeedingOrder() {
        return 1;
    }
}
