package com.momentus.projmanagement.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.momentus.foundation.common.model.BaseEntity;
import com.momentus.foundation.finitevalue.model.FiniteValue;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "project_milestones")
public class ProjectMilestone extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", nullable = true)
    @JsonIgnore
    Project project;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_status", referencedColumnName = "fv_code")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    FiniteValue milestoneStatus;


    @Column( name ="milestone_details", length = 755)
    String milestoneDetails;

    @Column
    LocalDate expectedCompletionDate;

    @Column
    LocalDate actualCompletionDate;

    @Column
    Boolean completed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public FiniteValue getMilestoneStatus() {
        return milestoneStatus;
    }

    public void setMilestoneStatus(FiniteValue milestoneStatus) {
        this.milestoneStatus = milestoneStatus;
    }

    public String getMilestoneDetails() {
        return milestoneDetails;
    }

    public void setMilestoneDetails(String milestoneDetails) {
        this.milestoneDetails = milestoneDetails;
    }

    public LocalDate getExpectedCompletionDate() {
        return expectedCompletionDate;
    }

    public void setExpectedCompletionDate(LocalDate expectedCompletionDate) {
        this.expectedCompletionDate = expectedCompletionDate;
    }

    public LocalDate getActualCompletionDate() {
        return actualCompletionDate;
    }

    public void setActualCompletionDate(LocalDate actualCompletionDate) {
        this.actualCompletionDate = actualCompletionDate;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @Override
    public void setParentObject(BaseEntity base) {
        setProject((Project)base);
    }

    @Override
    public Object getPK() {
        return id;
    }
}
