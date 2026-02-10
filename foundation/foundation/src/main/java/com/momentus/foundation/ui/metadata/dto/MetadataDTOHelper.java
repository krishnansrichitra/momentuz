package com.momentus.foundation.ui.metadata.dto;

import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.ui.metadata.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class MetadataDTOHelper {

  @Autowired GeneralMessages generalMessages;

  public UpdateViewMetadataDTO makeUpdateViewDTO(
      UpdateViewMetadata metadata, Locale locale, String mode) {
    UpdateViewMetadataDTO updateViewMetadataDTO = new UpdateViewMetadataDTO();
    updateViewMetadataDTO.setTitle(
        generalMessages.getMessage(mode, locale)
            + " "
            + generalMessages.getMessage(metadata.getEntity(), locale));
    updateViewMetadataDTO.setJsFile(metadata.getJsFile());
    if (metadata != null) {
      updateViewMetadataDTO.setEntity(metadata.getEntity());
      List<UpdateViewFieldDTO> updateViewFieldDTOS = new ArrayList<>();
      if (!CollectionUtils.isEmpty(metadata.getUpdateViewFields())) {
        for (UpdateViewField updateViewField : metadata.getUpdateViewFields()) {
          UpdateViewFieldDTO updateViewFieldDTO =
              new UpdateViewFieldDTO(
                  updateViewField.getId(),
                  updateViewField.getFieldKey(),
                  generalMessages.getMessage(updateViewField.getFieldKey(), locale),
                  updateViewField.getControl(),
                  updateViewField.getParam(),
                  updateViewField.getAccessor(),
                  updateViewField.getVisibility(),
                  updateViewField.getDataType(),
                  updateViewField.getParent());
          updateViewFieldDTOS.add(updateViewFieldDTO);
        }
      }
      updateViewMetadataDTO.setUpdateViewFields(updateViewFieldDTOS);
      List<UpdateViewButtonDTO> updateViewButtonDTOS = new ArrayList<>();
      if (!CollectionUtils.isEmpty(metadata.getUpdateViewButtons())) {
        for (UpdateViewButton updateViewButton : metadata.getUpdateViewButtons()) {
          UpdateViewButtonDTO updateViewButtonDTO =
              new UpdateViewButtonDTO(
                  updateViewButton.getId(),
                  updateViewButton.getButtonClass(),
                  updateViewButton.getJsMethod(),
                  generalMessages.getMessage(updateViewButton.getInnerText(), locale),
                  updateViewButton.getVisibility(),
                  updateViewButton.getSeqNo());
          updateViewButtonDTOS.add(updateViewButtonDTO);
        }
      }
      updateViewMetadataDTO.setUpdateViewButtons(updateViewButtonDTOS);
    }
    return updateViewMetadataDTO;
  }

  public ListMetadataDTO makeListMetadataDTO(ListMetadata listMetadata, Locale locale) {
    ListMetadataDTO listMetadataDTO = new ListMetadataDTO();
    if (listMetadata != null) {
      listMetadataDTO.setEntity(listMetadata.getEntity());
      if (!CollectionUtils.isEmpty(listMetadata.getListColumns())) {
        List<ListColumnDTO> listColumnDTOS = new ArrayList<>();
        for (ListColumn listColumn : listMetadata.getListColumns()) {
          ListColumnDTO listColumnDTO =
              new ListColumnDTO(
                  listColumn.getFieldKey(),
                  generalMessages.getMessage(listColumn.getFieldKey(), locale),
                  listColumn.getAccessor());
          listColumnDTOS.add(listColumnDTO);
        }
        listMetadataDTO.setListColumns(listColumnDTOS);
      } else {
        listMetadataDTO.setListColumns(new ArrayList<>());
      }
      if (!CollectionUtils.isEmpty(listMetadata.getFilterFields())) {
        List<FilterFieldDTO> filterFieldDTOS = new ArrayList<>();
        for (FilterField filterField : listMetadata.getFilterFields()) {
          FilterFieldDTO filterFieldDTO =
              new FilterFieldDTO(
                  filterField.getFieldKey(),
                  generalMessages.getMessage(filterField.getFieldKey(), locale),
                  filterField.getControl(),
                  filterField.getParam(),
                  filterField.getAccessor());
          filterFieldDTOS.add(filterFieldDTO);
        }
        listMetadataDTO.setFilterFields(filterFieldDTOS);
      } else {
        listMetadataDTO.setFilterFields(new ArrayList<>());
      }

      if (!CollectionUtils.isEmpty(listMetadata.getListButtons())) {
        List<ListButtonDTO> listButtonDTOS = new ArrayList<>();
        for (ListButton listButton : listMetadata.getListButtons()) {
          ListButtonDTO listButtonDTO =
              new ListButtonDTO(
                  listButton.getButtonClass(),
                  listButton.getJsMethod(),
                  generalMessages.getMessage(listButton.getInnerText(), locale));
          listButtonDTOS.add(listButtonDTO);
        }
        listMetadataDTO.setListButtons(listButtonDTOS);
      } else {
        listMetadataDTO.setListButtons(new ArrayList<>());
      }
    }
    return listMetadataDTO;
  }
}
