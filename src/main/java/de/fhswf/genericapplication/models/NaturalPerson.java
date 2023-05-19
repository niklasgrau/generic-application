package de.fhswf.genericapplication.models;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class NaturalPerson extends Contact {

    @Column(nullable = false)
    private String firstName;

    /**
     * Default constructor
     */
    public NaturalPerson() {
        super();
    }

    public NaturalPerson(String name, String firstName) {
        super(name);
        this.firstName = firstName;
    }

    public NaturalPerson(Contact contact) {
        super(contact);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
