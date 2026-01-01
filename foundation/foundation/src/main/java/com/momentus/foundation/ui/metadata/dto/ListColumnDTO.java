package com.momentus.foundation.ui.metadata.dto;

public class ListColumnDTO {

    String fieldkey;

    String fieldLabel;

    public String getFieldkey() {
        return fieldkey;
    }

    public void setFieldkey(String fieldkey) {
        this.fieldkey = fieldkey;
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public ListColumnDTO() {
    }

    public ListColumnDTO(String fieldkey, String fieldLabel) {
        this.fieldkey = fieldkey;
        this.fieldLabel = fieldLabel;
    }
}
