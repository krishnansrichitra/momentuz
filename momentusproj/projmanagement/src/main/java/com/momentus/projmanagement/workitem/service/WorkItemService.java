package com.momentus.projmanagement.workitem.service;

import com.momentus.foundation.accessgroup.model.User;
import com.momentus.foundation.accessgroup.repository.UserRepository;
import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.transaction.MomentusError;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.finitevalue.model.FiniteValue;
import com.momentus.foundation.finitevalue.service.FiniteValueService;
import com.momentus.foundation.generic.service.GenericService;
import com.momentus.foundation.login.service.AppUserDetailsService;
import com.momentus.projmanagement.project.service.ProjectService;
import com.momentus.projmanagement.workitem.dto.WorkItemDTO;
import com.momentus.projmanagement.workitem.dto.WorkItemDTOHelper;
import com.momentus.projmanagement.workitem.model.WorkItem;
import com.momentus.projmanagement.workitem.repository.WorkItemRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class WorkItemService extends GenericService {
  @Autowired WorkItemRepository workItemRepository;

  @Autowired GeneralMessages generalMessages;

  @Autowired
    FiniteValueService finiteValueService;

  @Autowired
    ProjectService projectService;

  @Autowired
    UserRepository userRepository;


  private static final Logger log = LoggerFactory.getLogger(WorkItemService.class);

  @Transactional
  public TransactionResponse createOrUpdateEntity(
      WorkItemDTO workItemDTO, ApplicationContext context) {
    try {
      log.info("Saving WorkItem =" + workItemDTO);
      WorkItem workItem = WorkItemDTOHelper.makeWorkItemFromDTO(workItemDTO);
      TransactionResponse response = basicValidation(workItem, context);
      if (response.hasHardError()) {
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return response;
      }
      response = loadSubObjects(workItem, context);
      if (response.hasHardError()) {
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return response;
      }
      response = advanceValidation(workItem, context);
      if (response.hasHardError()) {
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return response;
      }
      workItemRepository.save(workItem);
      response.setResponseStatus(TransactionResponse.RESPONSE_STATUS.SUCCESS);
      response.setResponseMesage("Work Item Saved");
      response.setTransactionEntity(workItem);
      return response;

    } catch (Exception ex) {
      log.error("Error while saving workitem", ex);
      TransactionResponse transactionResponse = new TransactionResponse();
      List<MomentusError> errors = new ArrayList<>();
      MomentusError momentusError =
          new MomentusError(
              GeneralMessages.UNIDIENTIFABLE_ERROR,
              generalMessages.getMessage(
                  GeneralMessages.UNIDIENTIFABLE_ERROR, context.getLocale()));
      errors.add(momentusError);
      transactionResponse.setMomentusErrorList(errors);
      return transactionResponse;
    }
  }



  private TransactionResponse basicValidation(WorkItem workItem, ApplicationContext context) {
      List<MomentusError> errors = new ArrayList<>();
      if (!StringUtils.hasLength(workItem.getTitle()))
      {
          MomentusError momentusError =
              new MomentusError(
                      WorkItemErrorCodes.TITLE_BLANK,
                      generalMessages.getMessage(
                              WorkItemErrorCodes.TITLE_BLANK, context.getLocale()));
          errors.add(momentusError);


      }
      if (!StringUtils.hasLength(workItem.getTitle()))
      {
          MomentusError momentusError =
                  new MomentusError(
                          WorkItemErrorCodes.DESC_BLANK,
                          generalMessages.getMessage(
                                  WorkItemErrorCodes.DESC_BLANK, context.getLocale()));
          errors.add(momentusError);

      }
      if (workItem.getType() == null )
      {
          MomentusError momentusError =
                  new MomentusError(
                          WorkItemErrorCodes.TYPE_BLANK,
                          generalMessages.getMessage(
                                  WorkItemErrorCodes.TYPE_BLANK, context.getLocale()));
          errors.add(momentusError);

      }
      if (workItem.getEstimate() != null && workItem.getEstimate() !=0 && workItem.getTimeUOM() == null){
          MomentusError momentusError =
                  new MomentusError(
                          WorkItemErrorCodes.UOM_NEEDED,
                          generalMessages.getMessage(
                                  WorkItemErrorCodes.UOM_NEEDED, context.getLocale()));
          errors.add(momentusError);

      }
      if(!CollectionUtils.isEmpty(errors))
                return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.FAILURE, errors,workItem);
      else
                return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);
  }

  private TransactionResponse loadSubObjects(WorkItem workItem, ApplicationContext context) {
    FiniteValue  type = finiteValueService.getFinitieValueByCode(workItem.getType().getFvCode());
    if( type != null ){
        workItem.setType(type);
    }
    if (workItem.getTimeUOM() != null){
        FiniteValue uom = finiteValueService.getFinitieValueByCode(workItem.getTimeUOM().getFvCode());
        if ( uom != null ){
            workItem.setTimeUOM(uom);
        }else{
            MomentusError momentusError =
                    new MomentusError(
                            WorkItemErrorCodes.UOM_NEEDED,
                            generalMessages.getMessage(
                                    WorkItemErrorCodes.UOM_NEEDED, context.getLocale()));
            return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.FAILURE, Arrays.asList(momentusError),workItem);
        }
    }
   if (workItem.getAssignee() != null && StringUtils.hasLength(workItem.getAssignee().getUserId())){
       User assignedUser = userRepository.findByUserId(workItem.getAssignee().getUserId()) .orElse(null);
       if (assignedUser != null && !assignedUser.isDeleted()){
           workItem.setAssignee(assignedUser);
       }else {
           MomentusError momentusError =
                   new MomentusError(
                           WorkItemErrorCodes.USER_ABSENT_NOT_VALID,
                           generalMessages.getMessage(
                                   WorkItemErrorCodes.USER_ABSENT_NOT_VALID,  new Object[] {"Assignee"},context.getLocale()));
           return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.FAILURE, Arrays.asList(momentusError),workItem);
       }

   }

      if (workItem.getOwner()!= null && StringUtils.hasLength(workItem.getOwner().getUserId())){
          User owner = userRepository.findByUserId(workItem.getOwner().getUserId()) .orElse(null);
          if (owner != null && !owner.isDeleted()){
              workItem.setAssignee(owner);
          }else {
              MomentusError momentusError =
                      new MomentusError(
                              WorkItemErrorCodes.USER_ABSENT_NOT_VALID,
                              generalMessages.getMessage(
                                      WorkItemErrorCodes.USER_ABSENT_NOT_VALID,  new Object[] {"Owner"},context.getLocale()));
              return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.FAILURE, Arrays.asList(momentusError),workItem);
          }

      }


    return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);
  }

  private TransactionResponse advanceValidation(WorkItem workItem, ApplicationContext context) {
    return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);
  }
}
