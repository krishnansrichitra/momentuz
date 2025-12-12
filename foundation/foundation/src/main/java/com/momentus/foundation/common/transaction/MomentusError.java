package com.momentus.foundation.common.transaction;

public class MomentusError {

    public enum ERROR_TYPE{ HARDERROR , SOFTERROR  } ;
    ERROR_TYPE errorType;

    String errorCode;

    String errorMessage;

    public ERROR_TYPE getErrorType() {
        return errorType;
    }

    public void setErrorType(ERROR_TYPE errorType) {
        this.errorType = errorType;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
