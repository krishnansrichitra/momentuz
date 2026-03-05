package com.momentus.projmanagement.workitem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.momentus.corefw.data.EntityProperties;
import com.momentus.foundation.accessgroup.model.User;
import com.momentus.foundation.finitevalue.model.FiniteValue;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import com.momentus.projmanagement.project.model.Project;
import jakarta.persistence.*;
import java.sql.Blob;
import java.time.LocalDate;

@Entity
@Table(name = "work_items")
public class WorkItem extends OrgBasedEntity {

  @Column
  @EntityProperties(isBK = true)
  String ticketNo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "wi_type", referencedColumnName = "fv_code")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  FiniteValue type;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "wi_status", referencedColumnName = "fv_code")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  FiniteValue status;

  @Column String title;

  @Column Blob description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "assignee", referencedColumnName = "userId", nullable = true)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  User assignee;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner", referencedColumnName = "userId", nullable = true)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  User owner;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id", nullable = true)
  @JsonIgnore
  Project project;

  @Column LocalDate dueDate;

  @Column Float estimate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "estimate_uom", referencedColumnName = "fv_code")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  FiniteValue timeUOM;

  @Column Float actuals;

  @Column LocalDate plannedStartDate;

  @Column LocalDate actualStartDate;

  @Column Blob acceptanceCriteria;

  @Column(length = 755)
  String label;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "blocked_by") // nullable = true if root divisions exist
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private WorkItem blockedBy;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id") // nullable = true if root divisions exist
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private WorkItem parent;

  public FiniteValue getType() {
    return type;
  }

  public void setType(FiniteValue type) {
    this.type = type;
  }

  public FiniteValue getStatus() {
    return status;
  }

  public void setStatus(FiniteValue status) {
    this.status = status;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Blob getDescription() {
    return description;
  }

  public void setDescription(Blob description) {
    this.description = description;
  }

  public User getAssignee() {
    return assignee;
  }

  public void setAssignee(User assignee) {
    this.assignee = assignee;
  }

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public Float getEstimate() {
    return estimate;
  }

  public void setEstimate(Float estimate) {
    this.estimate = estimate;
  }

  public FiniteValue getTimeUOM() {
    return timeUOM;
  }

  public void setTimeUOM(FiniteValue timeUOM) {
    this.timeUOM = timeUOM;
  }

  public Float getActuals() {
    return actuals;
  }

  public void setActuals(Float actuals) {
    this.actuals = actuals;
  }

  public LocalDate getPlannedStartDate() {
    return plannedStartDate;
  }

  public void setPlannedStartDate(LocalDate plannedStartDate) {
    this.plannedStartDate = plannedStartDate;
  }

  public LocalDate getActualStartDate() {
    return actualStartDate;
  }

  public void setActualStartDate(LocalDate actualStartDate) {
    this.actualStartDate = actualStartDate;
  }

  public Blob getAcceptanceCriteria() {
    return acceptanceCriteria;
  }

  public void setAcceptanceCriteria(Blob acceptanceCriteria) {
    this.acceptanceCriteria = acceptanceCriteria;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getTicketNo() {
    return ticketNo;
  }

  public void setTicketNo(String ticketNo) {
    this.ticketNo = ticketNo;
  }

  public WorkItem getBlockedBy() {
    return blockedBy;
  }

  public void setBlockedBy(WorkItem blockedBy) {
    this.blockedBy = blockedBy;
  }

  public WorkItem getParent() {
    return parent;
  }

  public void setParent(WorkItem parent) {
    this.parent = parent;
  }
}
