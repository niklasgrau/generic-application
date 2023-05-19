package de.fhswf.genericapplication.models;

import de.fhswf.genericapplication.annotations.InverseAssociation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Employee extends User {

    @InverseAssociation(inverseRelationName = "manager")
    @OneToMany(mappedBy = "manager", targetEntity = Project.class, fetch = FetchType.LAZY)
    @OrderBy("id")
    private List<Project> managerProjects = new ArrayList<>();

    @InverseAssociation(inverseRelationName = "deputyManager")
    @OneToMany(mappedBy = "deputyManager", targetEntity = Project.class, fetch = FetchType.LAZY)
    @OrderBy("id")
    private List<Project> deputyManagerProjects = new ArrayList<>();

    @InverseAssociation(inverseRelationName = "team")
    @ManyToMany(mappedBy = "team", targetEntity = Project.class, fetch = FetchType.LAZY)
    private Set<Project> teamProjects = new HashSet<>();

    @InverseAssociation(inverseRelationName = "employee")
    @OneToMany(mappedBy = "employee", targetEntity = Expenditure.class, fetch = FetchType.LAZY)
    @OrderBy("id")
    private List<Expenditure> expenditures = new ArrayList<>();

    /**
     * Default constructor
     */
    public Employee() {
        super();
    }

    public Employee(String email, String password, String firstName, String lastName) {
        super(email, password, firstName, lastName);
    }

    public List<Project> getManagerProjects() {
        return managerProjects;
    }

    public void setManagerProjects(List<Project> managerProjects) {
        this.managerProjects = managerProjects;
    }

    public List<Project> getDeputyManagerProjects() {
        return deputyManagerProjects;
    }

    public void setDeputyManagerProjects(List<Project> deputyManagerProjects) {
        this.deputyManagerProjects = deputyManagerProjects;
    }

    public Set<Project> getTeamProjects() {
        return teamProjects;
    }

    public void setTeamProjects(Set<Project> teamProjects) {
        this.teamProjects = teamProjects;
    }

    public List<Expenditure> getExpenditures() {
        return expenditures;
    }

    public void setExpenditures(List<Expenditure> expenditures) {
        this.expenditures = expenditures;
    }
}
