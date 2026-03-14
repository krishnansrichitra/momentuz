package com.momentus.projmanagement.workitem.dto;

import com.momentus.foundation.accessgroup.model.User;
import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.finitevalue.dto.FiniteValueDTO;
import com.momentus.projmanagement.project.model.Project;
import com.momentus.projmanagement.releases.model.Release;
import com.momentus.projmanagement.releases.model.Sprint;
import com.momentus.projmanagement.releases.model.Team;
import com.momentus.projmanagement.workitem.model.WorkItem;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import javax.sql.rowset.serial.SerialBlob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class WorkItemDTOHelper {

  @Autowired GeneralMessages generalMessages;

  public WorkItem makeWorkItemFromDTO(WorkItemDTO workItemDTO) throws Exception {

    WorkItem workItem = new WorkItem();
    workItem.setType(FiniteValueDTO.makeFiniteValue(workItemDTO.getType()));
    if (workItemDTO.getStatus() != null)
      workItem.setStatus(FiniteValueDTO.makeFiniteValue(workItemDTO.getStatus()));
    if (StringUtils.hasLength(workItemDTO.getWiNo())) workItem.setWiNo(workItemDTO.getWiNo());
    workItem.setSummary(workItemDTO.getSummary());
    workItem.setDescription(
        new SerialBlob(workItemDTO.getDescription().getBytes(StandardCharsets.UTF_8)));
    workItem.setPlannedStartDate(workItemDTO.getPlannedStartDate());
    workItem.setTimeUOM(FiniteValueDTO.makeFiniteValue(workItemDTO.getTimeUOM()));
    workItem.setEstimate(workItemDTO.getEstimate());
    workItem.setActualStartDate(workItemDTO.getActualStartDate());
    workItem.setActuals(workItemDTO.getActuals());
    Project project = new Project();
    project.setId(workItemDTO.getProjectId());
    workItem.setProject(project);
    User assignee = new User();
    assignee.setUserId(workItemDTO.getAssignee());
    workItem.setAssignee(assignee);
    if (StringUtils.hasLength(workItemDTO.getOwner())) {
      User owner = new User();
      owner.setUserId(workItemDTO.getOwner());
      workItem.setOwner(owner);
    }
    if (StringUtils.hasLength(workItemDTO.getParent())) {
      WorkItem parentWorkItem = new WorkItem();
      parentWorkItem.setWiNo(workItemDTO.getParent());
      workItem.setParent(parentWorkItem);
    }
    if (workItemDTO.getReleaseId() != null && workItemDTO.getReleaseId() > 0) {
      Release release = new Release();
      release.setId(workItemDTO.getReleaseId());
      workItem.setRelease(release);
    }
    if (workItemDTO.getTeamId() != null && workItemDTO.getTeamId() > 0) {
      Team team = new Team();
      team.setId(workItemDTO.getTeamId());
      workItem.setTeam(team);
    }
    if (workItemDTO.getSprintId() != null && workItemDTO.getSprintId() > 0) {
      Sprint sprint = new Sprint();
      sprint.setId(workItemDTO.getSprintId());
      workItem.setSprint(sprint);
    }
    if (StringUtils.hasLength(workItemDTO.getBlockedBy())) {
      WorkItem blockedBy = new WorkItem();
      blockedBy.setWiNo(workItemDTO.getBlockedBy());
      workItem.setBlockedBy(blockedBy);
    }
    if (StringUtils.hasLength(workItemDTO.getDependentOn())) {
      WorkItem dependendent = new WorkItem();
      dependendent.setWiNo(workItemDTO.getDependentOn());
      workItem.setDependentOn(dependendent);
    }
    if (StringUtils.hasLength(workItemDTO.getDuplicateOf())) {
      WorkItem duplicateOf = new WorkItem();
      duplicateOf.setWiNo(workItemDTO.getDuplicateOf());
      workItem.setDuplicateOf(duplicateOf);
    }
    if (workItemDTO.getReasonCode() != null
        && StringUtils.hasLength(workItemDTO.getReasonCode().getFvCode()))
      workItem.setInvalidReason(FiniteValueDTO.makeFiniteValue(workItemDTO.getReasonCode()));

    return workItem;
  }

  public WorkItemDTO makeWorkitemDTOfromWorkItem(WorkItem workItem, Locale ls) throws Exception {
    WorkItemDTO workItemDTO = new WorkItemDTO();
    workItemDTO.setType(
        new FiniteValueDTO(
            workItem.getType().getFvCode(),
            generalMessages.getMessage(workItem.getType().getFvValue(), ls)));
    workItemDTO.setStatus(
        new FiniteValueDTO(
            workItem.getStatus().getFvCode(),
            generalMessages.getMessage(workItem.getStatus().getFvValue(), ls)));
    if (workItem.getTimeUOM() != null)
      workItemDTO.setTimeUOM(
          new FiniteValueDTO(
              workItem.getTimeUOM().getFvCode(),
              generalMessages.getMessage(workItem.getTimeUOM().getFvValue(), ls)));
    if (workItem.getInvalidReason() != null)
      workItemDTO.setReasonCode(
          new FiniteValueDTO(
              workItem.getInvalidReason().getFvCode(),
              generalMessages.getMessage(workItem.getInvalidReason().getFvValue(), ls)));
    if (workItem.getProject() != null) {
      workItemDTO.setProjectId(workItem.getProject().getId());
    }
    if (workItem.getRelease() != null) {
      workItemDTO.setReleaseId(workItem.getRelease().getId());
    }
    if (workItem.getSprint() != null) {
      workItemDTO.setSprintId(workItem.getSprint().getId());
    }
    if (workItem.getTeam() != null) {
      workItemDTO.setTeamId(workItem.getTeam().getId());
    }
    if (workItem.getAssignee() != null) {
      workItemDTO.setAssignee(workItem.getAssignee().getUserId());
    }
    if (workItem.getOwner() != null) {
      workItemDTO.setOwner(workItem.getOwner().getUserId());
    }
    if (workItem.getAcceptanceCriteria() != null) {
      String text =
          new String(
              workItem
                  .getAcceptanceCriteria()
                  .getBytes(1, (int) workItem.getAcceptanceCriteria().length()),
              StandardCharsets.UTF_8);
      workItemDTO.setAcceptanceCritieria(text);
    }
    if (workItem.getDescription() != null) {
      String text =
          new String(
              workItem.getDescription().getBytes(1, (int) workItem.getDescription().length()),
              StandardCharsets.UTF_8);
      workItemDTO.setDescription(text);
    }
    workItemDTO.setActuals(workItem.getActuals());
    workItemDTO.setWiNo(workItem.getWiNo());
    if (workItem.getBlockedBy() != null)
      workItemDTO.setBlockedBy(workItem.getBlockedBy().getWiNo());
    if (workItem.getDependentOn() != null)
      workItemDTO.setDependentOn(workItem.getDependentOn().getWiNo());
    if (workItem.getDuplicateOf() != null)
      workItemDTO.setDuplicateOf(workItem.getDuplicateOf().getWiNo());
    if (workItem.getParent() != null) workItemDTO.setParent(workItem.getParent().getWiNo());

    workItemDTO.setActualStartDate(workItem.getActualStartDate());
    workItemDTO.setPlannedStartDate(workItem.getPlannedStartDate());
    workItemDTO.setDueDate(workItem.getDueDate());
    workItemDTO.setEstimate(workItem.getEstimate());
    workItemDTO.setLabel(workItem.getLabel());
    workItemDTO.setSummary(workItem.getSummary());
    return workItemDTO;
  }
}
