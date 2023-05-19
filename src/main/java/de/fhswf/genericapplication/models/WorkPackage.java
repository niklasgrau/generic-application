package de.fhswf.genericapplication.models;

import de.fhswf.genericapplication.annotations.InverseAssociation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "work_packages")
public class WorkPackage extends BaseEntity {
    @Column(nullable = false)
    private String designation;

    private String description;

    @Column(unique = true, nullable = false)
    private String ticketNumber;

    private boolean customerBillable;

    private WorkPackageTypeET type;

    @InverseAssociation(inverseRelationName = "workPackages")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectId", referencedColumnName = "id", nullable = false)
    private Project project;

    @InverseAssociation(inverseRelationName = "workPackage")
    @OneToMany(mappedBy = "workPackage", targetEntity = Expenditure.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @OrderBy("id")
    private List<Expenditure> expenditures = new ArrayList<>();

    /**
     * Default constructor
     */
    public WorkPackage() {
        super();
    }

    public WorkPackage(
            String designation,
            String description,
            String ticketNumber,
            boolean customerBillable,
            WorkPackageTypeET type,
            Project project
    ) {
        this.designation = designation;
        this.description = description;
        this.ticketNumber = ticketNumber;
        this.customerBillable = customerBillable;
        this.type = type;
        this.project = project;
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

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public boolean isCustomerBillable() {
        return customerBillable;
    }

    public void setCustomerBillable(boolean customerBillable) {
        this.customerBillable = customerBillable;
    }

    public WorkPackageTypeET getType() {
        return type;
    }

    public void setType(WorkPackageTypeET type) {
        this.type = type;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Expenditure> getExpenditures() {
        return expenditures;
    }

    public void setExpenditures(List<Expenditure> expenditures) {
        this.expenditures = expenditures;
    }
}
