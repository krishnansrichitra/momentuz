package com.momentus.foundation.ui.metadata.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.momentus.corefw.data.EntityProperties;
import com.momentus.foundation.profile.model.ProfileBasedEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "list_metadata")
public class ListMetadata extends ProfileBasedEntity {

    @Id
    @EntityProperties(isPK = true)
    String id;


    @Column
    String description;


    @OneToMany(mappedBy = "listMetadata")
    @OrderBy("seqNo ASC")
    List<FilterField> filterFields;

    @OneToMany(mappedBy = "listMetadata")
    @OrderBy("seqNo ASC")
    List<ListColumn> listColumns;

    @OneToMany(mappedBy = "listMetadata")
    @OrderBy("seqNo ASC")
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    @JsonIgnore
    public Object getPK() {
        return id;
    }
}

