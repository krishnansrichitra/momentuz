package com.momentus.projmanagement.workitem.dto;

import com.momentus.foundation.accessgroup.model.User;
import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.finitevalue.dto.FiniteValueDTO;
import com.momentus.projmanagement.project.model.Project;
import com.momentus.projmanagement.workitem.model.WorkItem;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import javax.sql.rowset.serial.SerialBlob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkItemDTOHelper {

  @Autowired GeneralMessages generalMessages;

  public WorkItem makeWorkItemFromDTO(WorkItemDTO workItemDTO) throws Exception {

    WorkItem workItem = new WorkItem();
    workItem.setType(FiniteValueDTO.makeFiniteValue(workItemDTO.getType()));
    workItem.setStatus(FiniteValueDTO.makeFiniteValue(workItemDTO.getStatus()));
    workItem.setWiNo(workItemDTO.getWiNo());
    workItem.setTitle(workItemDTO.getTitle());
    workItem.setDescription(
        new SerialBlob(workItemDTO.getDescription().getBytes(StandardCharsets.UTF_8)));
    workItem.setPlannedStartDate(workItemDTO.getPlannedStartDate());
    workItem.setTimeUOM(FiniteValueDTO.makeFiniteValue(workItemDTO.getTimeUOM()));
    workItem.setEstimate(workItemDTO.getEstimate());
    workItem.setActualStartDate(workItemDTO.getActualStartDate());
    workItem.setTimeUOM(FiniteValueDTO.makeFiniteValue(workItemDTO.getActualsUOM()));
    workItem.setActuals(workItemDTO.getActuals());
    Project project = new Project();
    project.setId(workItemDTO.getProjectId());
    workItem.setProject(project);
    User assignee = new User();
    assignee.setUserId(workItemDTO.getAssignee());
    workItem.setAssignee(assignee);
    User owner = new User();
    owner.setUserId(workItemDTO.getOwner());
    workItem.setOwner(owner);
    WorkItem parentWorkItem = new WorkItem();
    parentWorkItem.setWiNo(workItemDTO.getParent());
    workItem.setParent(parentWorkItem);
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
    if (workItem.getProject() != null) {
      workItemDTO.setProjectId(workItem.getProject().getId());
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
    workItemDTO.setActualStartDate(workItem.getActualStartDate());
    workItemDTO.setPlannedStartDate(workItem.getPlannedStartDate());
    workItemDTO.setDueDate(workItem.getDueDate());
    workItemDTO.setEstimate(workItem.getEstimate());
    workItemDTO.setLabel(workItem.getLabel());
    workItemDTO.setTitle(workItem.getTitle());
    return workItemDTO;
  }
}
