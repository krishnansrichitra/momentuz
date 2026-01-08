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

    @OneToMany(mappedBy = "listMetadata")
    List<ListButton> listButtons;


    @Column
    String entity;

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

    public List<ListColumn> getListColumns() {
        return listColumns;
    }

    public void setListColumns(List<ListColumn> listColumns) {
        this.listColumns = listColumns;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public List<ListButton> getListButtons() {
        return listButtons;
    }

    public void setListButtons(List<ListButton> listButtons) {
        this.listButtons = listButtons;
    }
}

