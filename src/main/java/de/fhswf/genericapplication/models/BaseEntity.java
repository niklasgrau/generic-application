package de.fhswf.genericapplication.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedAt")
    private Date updatedAt;

    @OneToOne
    @JoinColumn(name = "createdBy", referencedColumnName = "id")
    private User createdBy;

    @OneToOne
    @JoinColumn(name = "editedBy", referencedColumnName = "id")
    private User editedBy;

    @PrePersist
    protected void prePersist() {
        if (this.createdBy == null) {
            // When authentication is implemented, set the creation user here by:
            // this.createdBy = this.getCurrentAuthenticatedUser();
        }
    }

    @PreUpdate
    protected void preUpdate() {
        // When authentication is implemented, set the editing user here by:
        // this.editedBy = this.getCurrentAuthenticatedUser();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(User editedBy) {
        this.editedBy = editedBy;
    }
}
