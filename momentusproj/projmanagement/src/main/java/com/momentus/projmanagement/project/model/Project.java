package com.momentus.projmanagement.project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.momentus.corefw.data.EntityProperties;
import com.momentus.foundation.accessgroup.model.User;
import com.momentus.foundation.finitevalue.model.FiniteValue;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import com.momentus.projmanagement.basedata.client.model.Client;
import jakarta.persistence.*;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "projects")
public class Project extends OrgBasedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Client client;

    @Column
    @EntityProperties(isUnique = true)
    String projectCode ;

    @Column
    String projectTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_primary_owner", referencedColumnName = "userId", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    User projectPrimaryOwner;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_secondary_owner", referencedColumnName = "userId", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    User projectSecondaryOwner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_status", referencedColumnName = "fv_code")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    FiniteValue status;

    @Column
    LocalDate commenceDate ;

    @Column
    LocalDate targetEndDate ;

    @Column
    LocalDate actualEndDate;

    @Column
    Blob projectSummary;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id Asc")
    List<ProjectSOW> projectSOWs;


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public User getProjectPrimaryOwner() {
        return projectPrimaryOwner;
    }

    public void setProjectPrimaryOwner(User projectPrimaryOwner) {
        this.projectPrimaryOwner = projectPrimaryOwner;
    }

    public User getProjectSecondaryOwner() {
        return projectSecondaryOwner;
    }

    public void setProjectSecondaryOwner(User projectSecondaryOwner) {
        this.projectSecondaryOwner = projectSecondaryOwner;
    }

    public FiniteValue getStatus() {
        return status;
    }

    public void setStatus(FiniteValue status) {
        this.status = status;
    }

    public LocalDate getCommenceDate() {
        return commenceDate;
    }

    public void setCommenceDate(LocalDate commenceDate) {
        this.commenceDate = commenceDate;
    }

    public LocalDate getTargetEndDate() {
        return targetEndDate;
    }

    public void setTargetEndDate(LocalDate targetEndDate) {
        this.targetEndDate = targetEndDate;
    }

    public LocalDate getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(LocalDate actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public Blob getProjectSummary() {
        return projectSummary;
    }

    public void setProjectSummary(Blob projectSummary) {
        this.projectSummary = projectSummary;
    }

    public List<ProjectSOW> getProjectSOWs() {
        return projectSOWs;
    }

    public void setProjectSOWs(List<ProjectSOW> projectSOWs) {
        this.projectSOWs = projectSOWs;
    }

    @Override
    public Object getBKValue() {
        return projectTitle ;
    }

    @Override
    public String getBKField() {
        return "projectTitle";
    }

    @Override
    public void setBK(Object object) {
        super.setBK(object);
    }

    @Override
    public Map<String, Object> getBK() {
        return super.getBK();
    }
}
