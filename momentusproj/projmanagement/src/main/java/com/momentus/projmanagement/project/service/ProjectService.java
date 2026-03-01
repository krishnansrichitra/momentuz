package com.momentus.projmanagement.project.service;

import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.model.BaseEntity;
import com.momentus.foundation.common.transaction.MomentusError;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.finitevalue.model.FiniteValue;
import com.momentus.foundation.finitevalue.service.FiniteValueService;
import com.momentus.foundation.generic.service.GenericService;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import com.momentus.projmanagement.project.model.Project;
import com.momentus.projmanagement.project.model.ProjectMilestone;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class ProjectService extends GenericService {

  @Autowired GeneralMessages generalMessages;

  @Autowired FiniteValueService finiteValueService;

  @Override
  protected TransactionResponse validate(
      OrgBasedEntity entity, ApplicationContext context, boolean skipBKValidation) {
    TransactionResponse response = super.validate(entity, context, skipBKValidation);
    List<MomentusError> errors = validateData(entity, context);
    if (!CollectionUtils.isEmpty(errors)) {
      if (response.getMomentusErrorList() == null) response.setMomentusErrorList(new ArrayList<>());
      response.getMomentusErrorList().addAll(errors);
      response.setResponseStatus(TransactionResponse.RESPONSE_STATUS.FAILURE);
    }

    return response;
  }

  @Override
  protected void preSave(BaseEntity entity, ApplicationContext context) {
    Project project = (Project) entity;
    setMilestoneStatus(project);
  }

  private void setMilestoneStatus(Project project) {
    if (!CollectionUtils.isEmpty(project.getProjectMilestones())) {
      for (ProjectMilestone milestone : project.getProjectMilestones()) {
        LocalDate completedate = milestone.getActualCompletionDate();
        LocalDate expectedDate = milestone.getExpectedCompletionDate();
        if (milestone.getCompleted()) {
          if (completedate == null || expectedDate == null || completedate.isBefore(expectedDate))
            milestone.setMilestoneStatus(
                finiteValueService.getFinitieValueByCode(
                    ProjectConstants.MILESTONE_STATUS_ACHONTIME));
          else if (completedate.isAfter(expectedDate))
            milestone.setMilestoneStatus(
                finiteValueService.getFinitieValueByCode(ProjectConstants.MILESTONE_STATUS_DLCMP));

        } else {
          if (expectedDate == null || (expectedDate.isAfter(LocalDate.now()))) {
            milestone.setMilestoneStatus(
                finiteValueService.getFinitieValueByCode(ProjectConstants.MILESTONE_STATUS_SCHD));
          } else if (!expectedDate.isAfter(LocalDate.now())) {
            milestone.setMilestoneStatus(
                finiteValueService.getFinitieValueByCode(ProjectConstants.MILESTONE_STATUS_DLYD));
          }
        }
      }
    }
  }

  protected List<MomentusError> validateData(OrgBasedEntity entity, ApplicationContext context) {
    Project project = (Project) entity;
    List<MomentusError> errors = new ArrayList<>();

    if (!CollectionUtils.isEmpty(project.getProjectMilestones())) {
      for (ProjectMilestone milestone : project.getProjectMilestones()) {
        if (!StringUtils.hasLength(milestone.getMilestoneTitle())) {
          MomentusError momentusError =
              new MomentusError(
                  MomentusError.ERROR_TYPE.HARDERROR,
                  ProjectConstants.ERROR_MILESTONETITLE_SHOULDNOTBEBLANK,
                  generalMessages.getMessage(
                      ProjectConstants.ERROR_MILESTONETITLE_SHOULDNOTBEBLANK, context.getLocale()));
          if (!errors.contains(momentusError)) errors.add(momentusError);
        }
        if (Boolean.TRUE.equals(milestone.getCompleted())
            && FiniteValue.isSame(project.getStatus(), ProjectConstants.PROJECT_READY_STATUS_CD)) {
          MomentusError momentusError =
              new MomentusError(
                  MomentusError.ERROR_TYPE.HARDERROR,
                  ProjectConstants.ERROR_MILESTONE_NOTBECOMPL_PRJSTART,
                  generalMessages.getMessage(
                      ProjectConstants.ERROR_MILESTONE_NOTBECOMPL_PRJSTART, context.getLocale()));
          if (!errors.contains(momentusError)) errors.add(momentusError);
        }
        if (milestone.getExpectedCompletionDate() != null
            && project.getTargetEndDate() != null
            && milestone.getExpectedCompletionDate().isAfter(project.getTargetEndDate())) {
          MomentusError momentusError =
              new MomentusError(
                  MomentusError.ERROR_TYPE.HARDERROR,
                  ProjectConstants.ERROR_MILESTONEDATE_BEFORE_PROJEND,
                  generalMessages.getMessage(
                      ProjectConstants.ERROR_MILESTONEDATE_BEFORE_PROJEND, context.getLocale()));
          if (!errors.contains(momentusError)) errors.add(momentusError);
        }
      }
    }
    return errors;
  }
}
