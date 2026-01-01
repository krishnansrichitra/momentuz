package com.momentus.foundation.ui.metadata.dto;

import java.util.List;

public class ListMetadataDTO {

    String entity;

    List<FilterFieldDTO> filterFields;

    List<ListColumnDTO> listColumns ;

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

    public ListMetadataDTO() {
    }

    public ListMetadataDTO(String entity, List<FilterFieldDTO> filterFields, List<ListColumnDTO> listColumns) {
        this.entity = entity;
        this.filterFields = filterFields;
        this.listColumns = listColumns;
    }
}
