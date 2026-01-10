package com.momentus.foundation.ui.metadata.model;

import com.momentus.foundation.profile.model.ProfileBasedEntity;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name="updateview_metadata")
public class UpdateViewMetadata extends ProfileBasedEntity {

    @Id
    String id ;

    @Column
    String entity;

    @Column
    String description;

    @OneToMany(mappedBy = "updateViewMetadata")
    @OrderBy("seqNo ASC")
    List<UpdateViewField> updateViewFields ;

    @OneToMany(mappedBy = "updateViewMetadata")
    @OrderBy("seqNo ASC")
    List<UpdateViewButton> updateViewButtons;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UpdateViewField> getUpdateViewFields() {
        return updateViewFields;
    }

    public void setUpdateViewFields(List<UpdateViewField> updateViewFields) {
        this.updateViewFields = updateViewFields;
    }

    public List<UpdateViewButton> getUpdateViewButtons() {
        return updateViewButtons;
    }

    public void setUpdateViewButtons(List<UpdateViewButton> updateViewButtons) {
        this.updateViewButtons = updateViewButtons;
    }
}
