package com.momentus.foundation.ui.metadata.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "list_buttons")
public class ListButton {

    @Id
    String id;

    @ManyToOne()
    @JoinColumn(name = "list_metadata_id" , referencedColumnName = "id" ,nullable = true)
    @JsonIgnore
    ListMetadata listMetadata;


    @Column
    String buttonClass ;

    @Column
    String jsMethod;

    @Column
    String innerText ;

    @Column(name = "seq_no")
    BigDecimal seqNo;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ListMetadata getListMetadata() {
        return listMetadata;
    }

    public void setListMetadata(ListMetadata listMetadata) {
        this.listMetadata = listMetadata;
    }

    public String getButtonClass() {
        return buttonClass;
    }

    public void setButtonClass(String buttonClass) {
        this.buttonClass = buttonClass;
    }

    public String getJsMethod() {
        return jsMethod;
    }

    public void setJsMethod(String jsMethod) {
        this.jsMethod = jsMethod;
    }

    public String getInnerText() {
        return innerText;
    }

    public void setInnerText(String innerText) {
        this.innerText = innerText;
    }

    public BigDecimal getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(BigDecimal seqNo) {
        this.seqNo = seqNo;
    }
}
