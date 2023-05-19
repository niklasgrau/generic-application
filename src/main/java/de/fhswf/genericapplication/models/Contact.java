package de.fhswf.genericapplication.models;

import de.fhswf.genericapplication.annotations.InverseAssociation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Contact extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @InverseAssociation(inverseRelationName = "contact")
    @OneToMany(mappedBy = "contact", targetEntity = Offer.class, fetch = FetchType.LAZY)
    @OrderBy("id")
    private List<Offer> offers = new ArrayList<>();

    /**
     * Default constructor
     */
    public Contact() {
        super();
    }

    public Contact(String name) {
        this.name = name;
    }

    public Contact(Contact contact) {
        this.setId(contact.getId());
        this.setName(contact.getName());
        this.setOffers(contact.getOffers());
        this.setCreatedBy(contact.getCreatedBy());
        this.setEditedBy(contact.getEditedBy());
        this.setCreatedAt(contact.getCreatedAt());
        this.setUpdatedAt(contact.getUpdatedAt());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}
