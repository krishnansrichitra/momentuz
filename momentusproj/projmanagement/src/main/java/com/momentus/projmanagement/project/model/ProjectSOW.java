package com.momentus.projmanagement.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.momentus.foundation.common.model.BaseEntity;
import com.momentus.foundation.finitevalue.model.FiniteValue;
import jakarta.persistence.*;

import java.sql.Blob;

@Entity
@Table(name = "project_sow")
public class ProjectSOW extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", nullable = true)
    @JsonIgnore
    Project project;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sow_type", referencedColumnName = "fv_code")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    FiniteValue  sowType;

    @Column(name = "sow_details", length = 755)
    String sowDetails ;

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

    public FiniteValue getSowType() {
        return sowType;
    }

    public void setSowType(FiniteValue sowType) {
        this.sowType = sowType;
    }

    public String getSowDetails() {
        return sowDetails;
    }

    public void setSowDetails(String sowDetails) {
        this.sowDetails = sowDetails;
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
