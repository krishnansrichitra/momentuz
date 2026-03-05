package com.momentus.projmanagement.workitem.dto;

import com.momentus.foundation.accessgroup.model.User;
import com.momentus.foundation.finitevalue.dto.FiniteValueDTO;
import com.momentus.projmanagement.project.model.Project;
import com.momentus.projmanagement.workitem.model.WorkItem;

import javax.sql.rowset.serial.SerialBlob;
import java.nio.charset.StandardCharsets;


public class WorkItemDTOHelper {

    public WorkItem makeWorkItemFromDTO(WorkItemDTO workItemDTO) throws Exception
    {

        WorkItem  workItem = new WorkItem();
        workItem.setType(FiniteValueDTO.makeFiniteValue(workItemDTO.getType()));
        workItem.setStatus(FiniteValueDTO.makeFiniteValue(workItemDTO.getStatus()));
        workItem.setTicketNo(workItemDTO.getTicketNo());
        workItem.setTitle(workItemDTO.getTitle());
        workItem.setDescription(new SerialBlob(workItemDTO.getDescription().getBytes(StandardCharsets.UTF_8)));
        workItem.setPlannedStartDate(workItemDTO.getPlannedStartDate());
        workItem.setEstimateUOM(FiniteValueDTO.makeFiniteValue(workItemDTO.getEstimateUOM()));
        workItem.setEstimate(workItemDTO.getEstimate());
        workItem.setActualStartDate(workItemDTO.getActualStartDate());
        workItem.setActualsUOM(FiniteValueDTO.makeFiniteValue(workItemDTO.getActualsUOM()));
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
}
