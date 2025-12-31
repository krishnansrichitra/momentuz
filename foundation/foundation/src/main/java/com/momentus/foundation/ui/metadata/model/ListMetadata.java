package com.momentus.foundation.ui.metadata.model;

import com.momentus.foundation.profile.model.ProfileBasedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "list_metadata")
public class ListMetadata extends ProfileBasedEntity {

    @Column
    String description;


    @OneToMany(mappedBy = "listMetadata")
    List<FilterField> filterFields;

    @OneToMany(mappedBy = "listMetadata")
    List<ListColumn> listColumns;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<FilterField> getFilterFields() {
        return filterFields;
    }

    public void setFilterFields(List<FilterField> filterFields) {
        this.filterFields = filterFields;
    }
}

