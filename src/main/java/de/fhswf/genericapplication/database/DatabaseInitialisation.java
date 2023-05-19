package de.fhswf.genericapplication.database;

import de.fhswf.genericapplication.database.factories.TestFactory;
import de.fhswf.genericapplication.database.seeders.Seeder;
import de.fhswf.genericapplication.models.*;
import de.fhswf.genericapplication.repositories.GenericEntityRepository;
import de.fhswf.genericapplication.repositories.MetadataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * This class initialises the database with fresh test data on application startup.
 *
 * @author Niklas Grau
 */
@Component
public class DatabaseInitialisation implements CommandLineRunner {
    private final static Logger LOG = LoggerFactory.getLogger(DatabaseInitialisation.class);

    @Value("${database.seed}")
    private boolean shouldSeed;

    @Autowired
    List<Seeder> seeders;

    @Autowired
    GenericEntityRepository genericEntityRepository;

    @Autowired
    TestFactory testFactory;

    @Autowired
    MetadataRepository metadataRepository;

    @Override
    public void run(String... args) throws Exception {
        if (!this.shouldSeed) {
            return;
        }

        this.initDefaultData();

        this.seeders.sort(Comparator.comparingInt(Seeder::getSeedingOrder));
        for (Seeder seeder : this.seeders) {
            seeder.seed();
        }
    }

    private void initDefaultData() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = dateFormat.parse(dateFormat.format(new Date()));

        Employee user1 = new Employee("john.doe@mail.com", "password", "John", "Doe");
        Employee user2 = new Employee("jan.jansen@mail.com", "password", "Jan", "Jansen");
        Employee user3 = new Employee("max.mustermann@mail.com", "password", "Max", "Mustermann");
        Employee user4 = new Employee("erika.musterfrau@mail.com", "password", "Erika", "Musterfrau");
        this.genericEntityRepository.createAll(List.of(user1, user2, user3, user4));

        Contact juristicPerson1 = new JuristicPerson("Company GmbH", "123-456-789");
        Contact juristicPerson2 = new JuristicPerson("Limited Ltd.", "012-345-678");
        Contact naturalPerson1 = new NaturalPerson("Doe", "Jane");
        Contact naturalPerson2 = new NaturalPerson("Pietersen", "Piet");
        this.genericEntityRepository.createAll(List.of(juristicPerson1, juristicPerson2, naturalPerson1, naturalPerson2));

        InputStream dummyUploadFile = this.getClass().getResourceAsStream("dummy.pdf");
        UploadFile uploadFile = new UploadFile("dummy.pdf", "application/pdf", StreamUtils.copyToByteArray(dummyUploadFile));

        Offer offer1 = new Offer("1-1", 1999.99, date, "Software-Consulting", "IT-Consulting Microservices", date, uploadFile, OfferStatusET.CREATED, ProjectTypeET.FIXED_PRICE, naturalPerson1);
        Offer offer2 = new Offer("2-1", 138000.00, date, "ERP-Software", "E-Commerce ERP-Software", date, uploadFile, OfferStatusET.SENT, ProjectTypeET.SERVICE, juristicPerson1);
        Offer offer3 = new Offer("3-1", 59000.00, date, "Software", "Software", date, uploadFile, OfferStatusET.DECLINED, ProjectTypeET.TIME_AND_MATERIAL, juristicPerson2);
        this.genericEntityRepository.createAll(List.of(offer1, offer2, offer3));

        Order order1 = new Order("1234-56", date, null, offer1);
        Order order2 = new Order("7890-12", date, null, offer2);
        Order order3 = new Order("7890-34", date, null, offer2);
        this.genericEntityRepository.createAll(List.of(order1, order2, order3));

        Project project1 = new Project(date, date, "Consulting Microservice", "1-1", user1, null);
        Project project2 = new Project(date, date, "ERP Basissoftware", "2-1", user1, null);
        Project project3 = new Project(date, date, "ERP CRM-Modul", "2-2", user1, user2);
        project2.setTeam(new HashSet<>(List.of(user3)));
        project3.setTeam(new HashSet<>(List.of(user3, user4)));
        this.genericEntityRepository.createAll(List.of(project1, project2, project3));

        order1.setProjects(new HashSet<>(List.of(project1)));
        this.genericEntityRepository.update(order1);
        order2.setProjects(new HashSet<>(List.of(project2, project3)));
        this.genericEntityRepository.update(order2);

        WorkPackage workPackage1 = new WorkPackage("Analyse", "Analyse bestehender Architektur", "1-1", true, WorkPackageTypeET.CONSULTING, project1);
        WorkPackage workPackage2 = new WorkPackage("Besprechung", "Besprechung der Analyseergebnisse", "1-2", false, WorkPackageTypeET.DISCUSSION, project2);
        WorkPackage workPackage3 = new WorkPackage("Implementierung", "Implementierung des Basismoduls mit Authentifizierung", "2-1", false, WorkPackageTypeET.DEVELOPMENT, project2);
        WorkPackage workPackage4 = new WorkPackage("Implementierung", "Implementierung des CRM-Moduls", "2-2", false, WorkPackageTypeET.DEVELOPMENT, project3);
        this.genericEntityRepository.createAll(List.of(workPackage1, workPackage2, workPackage3, workPackage4));

        Expenditure expenditure1 = new Expenditure(date, date, "Analyse", true, user1, project1, workPackage1);
        Expenditure expenditure2 = new Expenditure(date, date, "Besprechung", false, user1, project1, workPackage2);
        Expenditure expenditure3 = new Expenditure(date, date, "Projekt initialisieren", false, user3, project2, workPackage3);
        Expenditure expenditure4 = new Expenditure(date, date, "Datenbank aufsetzen", false, user3, project2, workPackage3);
        this.genericEntityRepository.createAll(List.of(expenditure1, expenditure2, expenditure3, expenditure4));

        Test test1 = testFactory.create();
        Test testNullabled = testFactory.createNullabled();
        this.genericEntityRepository.createAll(List.of(test1, testNullabled));

        // Metadaten
        File file = ResourceUtils.getFile("classpath:de/fhswf/genericapplication/metadata/Metadata.xml");
        InputStream fileInputStream = new FileInputStream(file);
        Metadata metadata = new Metadata(100L, StreamUtils.copyToByteArray(fileInputStream));
        metadataRepository.save(metadata);
        LOG.info("Metadata Version: " + metadata.getVersion());
    }
}