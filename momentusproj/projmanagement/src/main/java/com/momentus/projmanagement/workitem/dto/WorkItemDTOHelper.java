package com.momentus.projmanagement.workitem.dto;

import com.momentus.foundation.accessgroup.model.User;
import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.finitevalue.dto.FiniteValueDTO;
import com.momentus.projmanagement.project.model.Project;
import com.momentus.projmanagement.workitem.model.WorkItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import javax.sql.rowset.serial.SerialBlob;

@Service
public class WorkItemDTOHelper {

    @Autowired
    GeneralMessages generalMessages;

  public  WorkItem makeWorkItemFromDTO(WorkItemDTO workItemDTO) throws Exception {

    WorkItem workItem = new WorkItem();
    workItem.setType(FiniteValueDTO.makeFiniteValue(workItemDTO.getType()));
    workItem.setStatus(FiniteValueDTO.makeFiniteValue(workItemDTO.getStatus()));
    workItem.setTicketNo(workItemDTO.getTicketNo());
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
    parentWorkItem.setTicketNo(workItemDTO.getParent());
    workItem.setParent(parentWorkItem);
    return workItem;
  }

  public  WorkItemDTO makeWorkitemDTOfromWorkItem(WorkItem workItem, Locale ls) {
    WorkItemDTO workItemDTO = new WorkItemDTO();
    workItemDTO.setType(new FiniteValueDTO(workItem.getType().getFvCode(),generalMessages.getMessage(workItem.getType().getFvValue(),ls)));
    workItemDTO.setStatus(new FiniteValueDTO(workItem.getStatus().getFvCode(),
            generalMessages.getMessage(workItem.getStatus().getFvValue(),ls)));
    if (workItem.getTimeUOM() != null)
        workItemDTO.setTimeUOM(new FiniteValueDTO(workItem.getTimeUOM().getFvCode(),generalMessages.getMessage(workItem.getTimeUOM().getFvValue(),ls)));
  

    return workItemDTO;
  }
}
