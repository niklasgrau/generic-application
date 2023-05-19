package de.fhswf.genericapplication.database.seeders;

import de.fhswf.genericapplication.database.factories.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Niklas Grau
 */
@Component
public class UserSeeder extends BaseSeeder {
    @Autowired
    UserFactory userFactory;

    @Override
    public void seed() throws Exception {
        this.repository.create(this.userFactory.createDefaultAdmin());
    }

    @Override
    public int getSeedingOrder() {
        return 0;
    }
}
