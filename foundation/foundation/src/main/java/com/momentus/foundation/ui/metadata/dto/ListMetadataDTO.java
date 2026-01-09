package com.momentus.foundation.ui.metadata.dto;

import java.util.ArrayList;
import java.util.List;

public class ListMetadataDTO {

    String entity;

    List<FilterFieldDTO> filterFields = new ArrayList();

    List<ListColumnDTO> listColumns = new ArrayList();

    List<ListButtonDTO> listButtons = new ArrayList();

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public List<FilterFieldDTO> getFilterFields() {
        return filterFields;
    }

    public void setFilterFields(List<FilterFieldDTO> filterFields) {
        this.filterFields = filterFields;
    }

    public List<ListColumnDTO> getListColumns() {
        return listColumns;
    }

    public void setListColumns(List<ListColumnDTO> listColumns) {
        this.listColumns = listColumns;
    }

    public List<ListButtonDTO> getListButtons() {
        return listButtons;
    }

    public void setListButtons(List<ListButtonDTO> listButtons) {
        this.listButtons = listButtons;
    }

    public ListMetadataDTO() {
    }

    public ListMetadataDTO(String entity, List<FilterFieldDTO> filterFields, List<ListColumnDTO> listColumns) {
        this.entity = entity;
        this.filterFields = filterFields;
        this.listColumns = listColumns;
    }

    public ListMetadataDTO(String entity, List<FilterFieldDTO> filterFields, List<ListColumnDTO> listColumns, List<ListButtonDTO> listButtons) {
        this.entity = entity;
        this.filterFields = filterFields;
        this.listColumns = listColumns;
        this.listButtons = listButtons;
    }
}
