package de.fhswf.genericapplication.database.factories;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Abstract base class implementing the base interface {@link Factory} of type {@link T}.
 * The class defines a faker class to generate random data and the function to create a list of instances of type {@link T}.
 *
 * @author Niklas Grau
 */
public abstract class BaseFactory<T> implements Factory<T> {
    @Autowired
    protected Faker faker;

    protected DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public List<T> create(int amount) {
        List<T> entities = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            entities.add(this.create());
        }

        return entities;
    }

    /**
     * Formats a given date to the default pattern of "yyyy-MM-dd HH:mm".
     *
     * @param date the date to change the format
     * @return
     */
    protected Date formatDate(Date date) {
        String dateString = this.dateFormat.format(date);
        try {
            return this.dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
