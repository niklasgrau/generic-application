package de.fhswf.genericapplication.models;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class JuristicPerson extends Contact {

    @Column(nullable = false)
    private String taxNumber;

    /**
     * Default constructor
     */
    public JuristicPerson() {
        super();
    }

    public JuristicPerson(String name, String taxNumber) {
        super(name);
        this.taxNumber = taxNumber;
    }

    public JuristicPerson(Contact contact) {
        super(contact);
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }
}
