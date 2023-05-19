package de.fhswf.genericapplication.database.seeders;

import de.fhswf.genericapplication.database.factories.JuristicPersonFactory;
import de.fhswf.genericapplication.database.factories.NaturalPersonFactory;
import de.fhswf.genericapplication.models.JuristicPerson;
import de.fhswf.genericapplication.models.NaturalPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Niklas Grau
 */
@Component
public class ContactSeeder extends BaseSeeder {
    @Autowired
    private JuristicPersonFactory juristicPersonFactory;

    @Autowired
    private NaturalPersonFactory naturalPersonFactory;

    @Override
    public void seed() throws Exception {
        List<JuristicPerson> juristicPersons = this.repository.createAll(this.juristicPersonFactory.create(15));
        List<NaturalPerson> naturalPersons = this.repository.createAll(this.naturalPersonFactory.create(15));
    }

    @Override
    public int getSeedingOrder() {
        return 0;
    }
}
