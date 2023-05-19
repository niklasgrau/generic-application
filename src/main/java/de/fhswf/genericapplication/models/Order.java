package de.fhswf.genericapplication.models;

import de.fhswf.genericapplication.annotations.InverseAssociation;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Column(nullable = false)
    private String externalOrderNumber;

    @Column(nullable = false)
    private Date receivedAt;

    @Embedded
    private UploadFile file;

    @InverseAssociation(inverseRelationName = "orders")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offerId", referencedColumnName = "id", nullable = false)
    private Offer offer;

    @InverseAssociation(inverseRelationName = "orders")
    @ManyToMany(targetEntity = Project.class, fetch = FetchType.LAZY)
    @JoinTable(
            name = "orders_projects",
            joinColumns = @JoinColumn(name = "orderId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "projectId", referencedColumnName = "id"))
    private Set<Project> projects = new HashSet<>();

    /**
     * Default constructor
     */
    public Order() {
        super();
    }

    public Order(String externalOrderNumber, Date receivedAt, UploadFile file, Offer offer) {
        this.externalOrderNumber = externalOrderNumber;
        this.receivedAt = receivedAt;
        this.file = file;
        this.offer = offer;
    }

    public String getExternalOrderNumber() {
        return externalOrderNumber;
    }

    public void setExternalOrderNumber(String externalOrderNumber) {
        this.externalOrderNumber = externalOrderNumber;
    }

    public Date getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Date receivedAt) {
        this.receivedAt = receivedAt;
    }

    public UploadFile getFile() {
        return file;
    }

    public void setFile(UploadFile file) {
        this.file = file;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
