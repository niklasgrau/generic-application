package de.fhswf.genericapplication.database.factories;

import de.fhswf.genericapplication.models.Offer;
import de.fhswf.genericapplication.models.OfferStatusET;
import de.fhswf.genericapplication.models.ProjectTypeET;
import de.fhswf.genericapplication.models.UploadFile;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Niklas Grau
 */
@Component
public class OfferFactory extends BaseFactory<Offer> {
    @Override
    public Offer create() {
        return new Offer(
                String.format("%s-%s", this.faker.number().digits(3), this.faker.number().digits(5)),
                this.faker.number().randomDouble(2, 0, 10000000),
                this.formatDate(this.faker.date().future(1, TimeUnit.DAYS)),
                this.faker.lorem().sentence(),
                this.faker.lorem().paragraph(2),
                this.formatDate(this.faker.date().future(30, 7, TimeUnit.DAYS)),
                new UploadFile(),
                this.faker.options().option(OfferStatusET.class),
                this.faker.options().option(ProjectTypeET.class),
                null
        );
    }
}
