package com.momentus.projmanagement.workitem.dto;

import com.momentus.foundation.finitevalue.dto.FiniteValueDTO;
import java.time.LocalDate;

public class WorkItemDTO {

  FiniteValueDTO type;

  FiniteValueDTO status;

  String wiNo;

  String title;

  String description;

  String assignee;

  String owner;

  Long projectId;

  LocalDate dueDate;

  LocalDate plannedStartDate;

  LocalDate actualStartDate;

  FiniteValueDTO timeUOM;

  Float estimate;

  FiniteValueDTO actualsUOM;

  Float actuals;

  String acceptanceCritieria;

  String label;

  String blockedBy;

  String parent;

  public FiniteValueDTO getType() {
    return type;
  }

  public void setType(FiniteValueDTO type) {
    this.type = type;
  }

  public FiniteValueDTO getStatus() {
    return status;
  }

  public void setStatus(FiniteValueDTO status) {
    this.status = status;
  }

    public String getWiNo() {
        return wiNo;
    }

    public void setWiNo(String wiNo) {
        this.wiNo = wiNo;
    }

    public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public Long getProjectId() {
    return projectId;
  }

  public void setProjectId(Long projectId) {
    this.projectId = projectId;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
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

  public FiniteValueDTO getTimeUOM() {
    return timeUOM;
  }

  public void setTimeUOM(FiniteValueDTO timeUOM) {
    this.timeUOM = timeUOM;
  }

  public Float getEstimate() {
    return estimate;
  }

  public void setEstimate(Float estimate) {
    this.estimate = estimate;
  }

  public FiniteValueDTO getActualsUOM() {
    return actualsUOM;
  }

  public void setActualsUOM(FiniteValueDTO actualsUOM) {
    this.actualsUOM = actualsUOM;
  }

  public Float getActuals() {
    return actuals;
  }

  public void setActuals(Float actuals) {
    this.actuals = actuals;
  }

  public String getAcceptanceCritieria() {
    return acceptanceCritieria;
  }

  public void setAcceptanceCritieria(String acceptanceCritieria) {
    this.acceptanceCritieria = acceptanceCritieria;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getBlockedBy() {
    return blockedBy;
  }

  public void setBlockedBy(String blockedBy) {
    this.blockedBy = blockedBy;
  }

  public String getParent() {
    return parent;
  }

  public void setParent(String parent) {
    this.parent = parent;
  }

  @Override
  public String toString() {
    return "WorkItemDTO{"
        + "type="
        + type
        + ", status="
        + status
        + ", wiNo='"
        + wiNo
        + '\''
        + ", title='"
        + title
        + '\''
        + ", description='"
        + description
        + '\''
        + ", assignee='"
        + assignee
        + '\''
        + ", owner='"
        + owner
        + '\''
        + ", projectId="
        + projectId
        + ", dueDate="
        + dueDate
        + ", plannedStartDate="
        + plannedStartDate
        + ", actualStartDate="
        + actualStartDate
        + ", timeUOM="
        + timeUOM
        + ", estimate="
        + estimate
        + ", actualsUOM="
        + actualsUOM
        + ", actuals="
        + actuals
        + ", acceptanceCritieria='"
        + acceptanceCritieria
        + '\''
        + ", label='"
        + label
        + '\''
        + ", blockedBy='"
        + blockedBy
        + '\''
        + ", parent='"
        + parent
        + '\''
        + '}';
  }
}
