package com.momentus.foundation.ui.metadata.dto;

public class FilterFieldDTO {

    String fieldKey;

    String fieldLabel ;

    String control ;

    String param;

    String accessor;

    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public FilterFieldDTO() {
    }

    public FilterFieldDTO(String fieldKey, String fieldLabel, String control, String param,String accessor) {
        this.fieldKey = fieldKey;
        this.fieldLabel = fieldLabel;
        this.control = control;
        this.param = param;
        this.accessor = accessor;
    }

    public String getAccessor() {
        return accessor;
    }

    public void setAccessor(String accessor) {
        this.accessor = accessor;
    }
}
