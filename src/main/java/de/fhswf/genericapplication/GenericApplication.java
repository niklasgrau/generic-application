package de.fhswf.genericapplication;

import com.github.javafaker.Faker;
import de.fhswf.genericapplication.models.BaseEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Locale;

@SpringBootApplication
public class GenericApplication {
    public static final String PACKAGE_NAME_MODELS = BaseEntity.class.getPackageName();

    @Bean
    public Faker faker() {
        return new Faker(Locale.GERMANY);
    }

    public static void main(String[] args) {
        SpringApplication.run(GenericApplication.class, args);
    }
}
