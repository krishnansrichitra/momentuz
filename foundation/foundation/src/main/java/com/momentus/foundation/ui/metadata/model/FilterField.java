package com.momentus.foundation.ui.metadata.model;

import jakarta.persistence.*;

@Entity
@Table(name = "filter_field")
public class FilterField {

    @Id
    Long id;

    @ManyToOne()
    @JoinColumn(name = "list_metadata_id" , referencedColumnName = "id" ,nullable = true)
    ListMetadata listMetadata;

    String fieldKey;

    String control;

    public ListMetadata getListMetadata() {
        return listMetadata;
    }

    public void setListMetadata(ListMetadata listMetadata) {
        this.listMetadata = listMetadata;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
