package com.momentus.foundation.common.transaction;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TransactionResponse {

    private final static String RESULT = "Result";
    private final static String errors = "Errors";

    public  enum RESPONSE_STATUS{ SUCCESS , FAILURE , WARNING };
    RESPONSE_STATUS responseStatus;

    List<MomentusError> momentusErrorList;

    public RESPONSE_STATUS getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(RESPONSE_STATUS responseStatus) {
        this.responseStatus = responseStatus;
    }

    public TransactionResponse(RESPONSE_STATUS responseStatus) {

        this.responseStatus = responseStatus;
    }

    public TransactionResponse() {
    }

    public List<MomentusError> getMomentusErrorList() {
        return momentusErrorList;
    }

    public void setMomentusErrorList(List<MomentusError> momentusErrorList) {
        this.momentusErrorList = momentusErrorList;
    }

    public Map<String,Object> convertToMap()
    {
        Map<String,Object> map = new LinkedHashMap<>();
        map.put(RESULT,responseStatus);
        map.put(errors,momentusErrorList) ;
        return map;
    }
}



