package de.fhswf.genericapplication.models;

import de.fhswf.genericapplication.annotations.InverseAssociation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "offers")
public class Offer extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String number;

    @Column(nullable = false)
    private Double sum;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String designation;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Date validUntil;

    @Embedded
    private UploadFile file;

    @Column(nullable = false)
    private OfferStatusET status;

    @Column(nullable = false)
    private ProjectTypeET type;

    @InverseAssociation(inverseRelationName = "offers")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contactId", referencedColumnName = "id", nullable = false)
    private Contact contact;

    @InverseAssociation(inverseRelationName = "offer")
    @OneToMany(mappedBy = "offer", targetEntity = Order.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("id")
    private List<Order> orders = new ArrayList<>();

    /**
     * Default constructor
     */
    public Offer() {
        super();
    }

    public Offer(String number, Double sum, Date date, String designation, String description, Date validUntil, UploadFile file, OfferStatusET status, ProjectTypeET type, Contact contact) {
        this.number = number;
        this.sum = sum;
        this.date = date;
        this.designation = designation;
        this.description = description;
        this.validUntil = validUntil;
        this.file = file;
        this.status = status;
        this.type = type;
        this.contact = contact;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }

    public UploadFile getFile() {
        return file;
    }

    public void setFile(UploadFile file) {
        this.file = file;
    }

    public OfferStatusET getStatus() {
        return status;
    }

    public void setStatus(OfferStatusET status) {
        this.status = status;
    }

    public ProjectTypeET getType() {
        return type;
    }

    public void setType(ProjectTypeET type) {
        this.type = type;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
