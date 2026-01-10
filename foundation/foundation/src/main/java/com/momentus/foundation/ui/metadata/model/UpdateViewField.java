package com.momentus.foundation.ui.metadata.model;

import com.momentus.foundation.profile.model.ProfileBasedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;


public class UpdateViewField  {

    String  id;

    String fieldKey;

    String control;

    String param;

    String accessor;


    BigDecimal seqNo;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
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

    public String getAccessor() {
        return accessor;
    }

    public void setAccessor(String accessor) {
        this.accessor = accessor;
    }


    public BigDecimal getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(BigDecimal seqNo) {
        this.seqNo = seqNo;
    }
}
