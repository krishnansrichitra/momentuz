package com.momentus.projmanagement.releases.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.momentus.corefw.data.EntityProperties;
import com.momentus.foundation.accessgroup.model.User;
import com.momentus.foundation.finitevalue.model.FiniteValue;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import com.momentus.projmanagement.project.model.Project;
import jakarta.persistence.*;

@Entity
@Table(name = "releases")
public class Release extends OrgBasedEntity {

  @Column
  @EntityProperties(isBK = true)
  String releaseNo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id", nullable = true)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  Project project;

  @Column Long year;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner", referencedColumnName = "userId", nullable = true)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  User owner;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "release_status", referencedColumnName = "fv_code")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  FiniteValue status;

  public String getReleaseNo() {
    return releaseNo;
  }

  public void setReleaseNo(String releaseNo) {
    this.releaseNo = releaseNo;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public Long getYear() {
    return year;
  }

  public void setYear(Long year) {
    this.year = year;
  }

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public FiniteValue getStatus() {
    return status;
  }

  public void setStatus(FiniteValue status) {
    this.status = status;
  }
}
