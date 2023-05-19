package de.fhswf.genericapplication.models;

import de.fhswf.genericapplication.annotations.InverseAssociation;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "expenditures")
public class Expenditure extends BaseEntity {
    @Column(nullable = false)
    private Date startDate;

    private Date endDate;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    private boolean customerBillable;

    @InverseAssociation(inverseRelationName = "expenditures")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    private Employee employee;

    @InverseAssociation(inverseRelationName = "expenditures")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectId", referencedColumnName = "id", nullable = false)
    private Project project;

    @InverseAssociation(inverseRelationName = "expenditures")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workPackageId", referencedColumnName = "id")
    private WorkPackage workPackage;

    /**
     * Default constructor
     */
    public Expenditure() {
        super();
    }

    public Expenditure(
            Date startDate,
            Date endDate,
            String description,
            boolean customerBillable,
            Employee employee,
            Project project,
            WorkPackage workPackage
    ) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.customerBillable = customerBillable;
        this.employee = employee;
        this.project = project;
        this.workPackage = workPackage;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCustomerBillable() {
        return customerBillable;
    }

    public void setCustomerBillable(boolean customerBillable) {
        this.customerBillable = customerBillable;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public WorkPackage getWorkPackage() {
        return workPackage;
    }

    public void setWorkPackage(WorkPackage workPackage) {
        this.workPackage = workPackage;
    }
}
