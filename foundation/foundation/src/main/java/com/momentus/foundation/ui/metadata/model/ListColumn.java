package com.momentus.foundation.ui.metadata.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "list_columns")
public class ListColumn {
    @Id
    Long id;

    @ManyToOne()
    @JoinColumn(name = "list_metadata_id" , referencedColumnName = "id" ,nullable = true)
    @JsonIgnore
    ListMetadata listMetadata;

    @Column
    String fieldKey;

    @Column
    String accessor;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessor() {
        return accessor;
    }

    public void setAccessor(String accessor) {
        this.accessor = accessor;
    }
}
