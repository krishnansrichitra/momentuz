package com.momentus.foundation.finitevalue.model;

import jakarta.persistence.*;

@Entity
@Table(name = "finite_value")
public class FiniteValue {

    @Id
    @Column(name ="fv_code")
    String fvCode;

    @Column(name="fv_value")
    String fvValue;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "group_code" ,referencedColumnName = "group_code")
    FiniteGroup finiteGroup;

    public String getFvCode() {
        return fvCode;
    }

    public void setFvCode(String fvCode) {
        this.fvCode = fvCode;
    }

    public String getFvValue() {
        return fvValue;
    }

    public void setFvValue(String fvValue) {
        this.fvValue = fvValue;
    }

    public FiniteGroup getFiniteGroup() {
        return finiteGroup;
    }

    public void setFiniteGroup(FiniteGroup finiteGroup) {
        this.finiteGroup = finiteGroup;
    }

    public FiniteValue(String fvCode) {
        this.fvCode = fvCode;
    }

    public FiniteValue() {
    }
}
