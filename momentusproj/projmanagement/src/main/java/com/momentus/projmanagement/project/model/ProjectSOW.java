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
    @JoinColumn(name = "note_type", referencedColumnName = "fv_code")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    FiniteValue noteType;

    @Column
    Blob sowDetails ;

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

    public FiniteValue getNoteType() {
        return noteType;
    }

    public void setNoteType(FiniteValue noteType) {
        this.noteType = noteType;
    }

    public Blob getSowDetails() {
        return sowDetails;
    }

    public void setSowDetails(Blob sowDetails) {
        this.sowDetails = sowDetails;
    }

    @Override
    public void setParentObject(BaseEntity base) {
        setProject((Project)base);
    }
}
