package com.momentus.foundation.common.transaction;

import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.model.BaseEntity;
import java.util.*;
import org.springframework.util.CollectionUtils;

public class TransactionResponse {

  private static final String RESULT = "Result";
  private static final String errors = "Errors";
  private static final String messages = "Messages";

  public enum RESPONSE_STATUS {
    SUCCESS,
    FAILURE,
    WARNING
  };

  String responseMesage;

  RESPONSE_STATUS responseStatus;

  List<MomentusError> momentusErrorList;

  BaseEntity transactionEntity;

  public RESPONSE_STATUS getResponseStatus() {
    return responseStatus;
  }

  public void setResponseStatus(RESPONSE_STATUS responseStatus) {
    this.responseStatus = responseStatus;
  }

  public TransactionResponse(RESPONSE_STATUS responseStatus) {

    this.responseStatus = responseStatus;
  }

  public TransactionResponse(RESPONSE_STATUS responseStatus, BaseEntity transactionEntity) {
    this.responseStatus = responseStatus;
    this.transactionEntity = transactionEntity;
  }

  public TransactionResponse(
      RESPONSE_STATUS responseStatus,
      List<MomentusError> momentusErrorList,
      BaseEntity transactionEntity) {
    this.responseStatus = responseStatus;
    this.momentusErrorList = momentusErrorList;
    this.transactionEntity = transactionEntity;
  }

  public TransactionResponse() {}

  public List<MomentusError> getMomentusErrorList() {
    return momentusErrorList;
  }

  public void setMomentusErrorList(List<MomentusError> momentusErrorList) {
    this.momentusErrorList = momentusErrorList;
  }

  public void addMomentusError(MomentusError momentusError) {
    if (momentusErrorList == null) {
      momentusErrorList = new ArrayList<>();
    }
    momentusErrorList.add(momentusError);
  }

  public Map<String, Object> convertToMap() {
    Map<String, Object> map = new LinkedHashMap<>();
    map.put(RESULT, responseStatus);
    map.put(errors, momentusErrorList);
    map.put(messages, Arrays.asList(responseMesage));
    return map;
  }

  public List<String> getErrorMessages(ApplicationContext context) {
    List<String> result = new ArrayList<>();
    if (!CollectionUtils.isEmpty(momentusErrorList)) {
      for (MomentusError error : momentusErrorList) {
        result.add(error.getErrorMessage());
      }
    }
    return result;
  }

  public Map<String, Object> errorMap() {
    Map<String, Object> errors = new HashMap<>();
    errors.put("errors", momentusErrorList);
    return errors;
  }

  public boolean hasHardError() {
    return (momentusErrorList != null && momentusErrorList.size() > 0);
  }

  public BaseEntity getTransactionEntity() {
    return transactionEntity;
  }

  public void setTransactionEntity(BaseEntity transactionEntity) {
    this.transactionEntity = transactionEntity;
  }

  public String getResponseMesage() {
    return responseMesage;
  }

  public void setResponseMesage(String responseMesage) {
    this.responseMesage = responseMesage;
  }
}
