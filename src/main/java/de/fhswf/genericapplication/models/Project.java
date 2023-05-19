package de.fhswf.genericapplication.models;

import de.fhswf.genericapplication.annotations.InverseAssociation;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "projects")
public class Project extends BaseEntity {

    private Date startDate;

    private Date endDate;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String number;

    @InverseAssociation(inverseRelationName = "projects")
    @ManyToMany(mappedBy = "projects", targetEntity = Order.class, fetch = FetchType.LAZY)
    private Set<Order> orders = new HashSet<>();

    @InverseAssociation(inverseRelationName = "project")
    @OneToMany(mappedBy = "project", targetEntity = WorkPackage.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("id")
    private List<WorkPackage> workPackages = new ArrayList<>();

    @InverseAssociation(inverseRelationName = "project")
    @OneToMany(mappedBy = "project", targetEntity = Expenditure.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("id")
    private List<Expenditure> expenditures = new ArrayList<>();

    @InverseAssociation(inverseRelationName = "managerProjects")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "managerUserId", referencedColumnName = "id", nullable = false)
    private Employee manager;

    @InverseAssociation(inverseRelationName = "deputyManagerProjects")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deputyManagerUserId", referencedColumnName = "id")
    private Employee deputyManager;

    @InverseAssociation(inverseRelationName = "teamProjects")
    @ManyToMany(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinTable(
            name = "projects_users",
            joinColumns = @JoinColumn(name = "projectId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"))
    private Set<Employee> team = new HashSet<>();

    /**
     * Default constructor
     */
    public Project() {
        super();
    }

    public Project(Date startDate, Date endDate, String name, String number, Employee manager, Employee deputyManager) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.number = number;
        this.manager = manager;
        this.deputyManager = deputyManager;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public List<WorkPackage> getWorkPackages() {
        return workPackages;
    }

    public void setWorkPackages(List<WorkPackage> workPackages) {
        this.workPackages = workPackages;
    }

    public List<Expenditure> getExpenditures() {
        return expenditures;
    }

    public void setExpenditures(List<Expenditure> expenditures) {
        this.expenditures = expenditures;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public Employee getDeputyManager() {
        return deputyManager;
    }

    public void setDeputyManager(Employee deputyManager) {
        this.deputyManager = deputyManager;
    }

    public Set<Employee> getTeam() {
        return team;
    }

    public void setTeam(Set<Employee> team) {
        this.team = team;
    }
}
