package com.momentus.projmanagement.workitem.service;

import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.transaction.MomentusError;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.projmanagement.workitem.dto.WorkItemDTO;
import com.momentus.projmanagement.workitem.dto.WorkItemDTOHelper;
import com.momentus.projmanagement.workitem.model.WorkItem;
import com.momentus.projmanagement.workitem.repository.WorkItemRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class WorkItemService {
  @Autowired WorkItemRepository workItemRepository;

  @Autowired GeneralMessages generalMessages;

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

    return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);
  }

  private TransactionResponse loadSubObjects(WorkItem workItem, ApplicationContext context) {

    return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);
  }

  private TransactionResponse advanceValidation(WorkItem workItem, ApplicationContext context) {
    return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);
  }
}
