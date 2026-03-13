package com.momentus.projmanagement.releases.service;

import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.generic.service.GenericService;
import com.momentus.projmanagement.releases.dto.SprintGenerationDTO;
import com.momentus.projmanagement.releases.model.Release;
import com.momentus.projmanagement.releases.model.Sprint;
import com.momentus.projmanagement.releases.model.Team;
import com.momentus.projmanagement.releases.repository.SprintRepository;
import com.momentus.projmanagement.workitem.service.WorkItemErrorCodes;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SprintService extends GenericService {

  @Autowired SprintRepository sprintRepository;

  @Autowired GenericService genericService;

  @Autowired GeneralMessages generalMessages;

  public TransactionResponse generateSprints(
      ApplicationContext context, SprintGenerationDTO generationDTO) {
    List<Sprint> sprints = generateSprintObjects(generationDTO, context);
    Integer count = 0;
    for (Sprint sprint : sprints) {
      if (sprint.getTeam() != null) {
        Team team =
            (Team) genericService.findById(sprint.getTeam().getId(), Team.class, context, true);
        if (team != null) {
          sprint.setTeam(team);
          sprint.setScrumMaster(team.getScrumMaster());
          sprint.setProductOwner(team.getProductOwner());
        }
      }
      if (sprint.getRelease() != null) {
        Release release =
            (Release)
                genericService.findById(sprint.getRelease().getId(), Release.class, context, true);
        if (release != null) {
          sprint.setRelease(release);
          sprint.setProject(release.getProject());
        }
      }
      TransactionResponse response =
          genericService.adaptValidateAndSaveEntity(sprint, context, false);
      if (!response.hasHardError()
          && response.getResponseStatus().equals(TransactionResponse.RESPONSE_STATUS.SUCCESS)) {
        count++;
      }
    }
    TransactionResponse response =
        new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);
    response.setResponseMesage(
        generalMessages.getMessage(
            WorkItemErrorCodes.SPRINT_SAVED,
            new Object[] {String.valueOf(count)},
            context.getLocale()));
    return response;
  }

  private List<Sprint> generateSprintObjects(
      SprintGenerationDTO generationDTO, ApplicationContext context) {
    List<Sprint> returnList = new ArrayList<>();
    int digits = String.valueOf(generationDTO.getSeqEnd()).length();
    for (int index = generationDTO.getSeqStart(); index <= generationDTO.getSeqEnd(); index++) {
      Sprint sprint = new Sprint();
      sprint.setOrgId(context.getOrganization());
      String mainpart = String.format("%0" + digits + "d", index);
      sprint.setSprintNo(generationDTO.getPrefix() + "." + mainpart);
      if (generationDTO.getTeamId() != null && generationDTO.getTeamId() > 0) {
        Team team = new Team();
        team.setId(generationDTO.getTeamId());
        sprint.setTeam(team);
      }
      if (generationDTO.getReleaseId() != null && generationDTO.getReleaseId() > 0) {
        Release release = new Release();
        release.setId(generationDTO.getReleaseId());
        sprint.setRelease(release);
      }
      returnList.add(sprint);
    }
    return returnList;
  }
}
