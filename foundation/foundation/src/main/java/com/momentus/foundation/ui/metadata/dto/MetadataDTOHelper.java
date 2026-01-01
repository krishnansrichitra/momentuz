package com.momentus.foundation.ui.metadata.dto;

import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.ui.metadata.model.FilterField;
import com.momentus.foundation.ui.metadata.model.ListColumn;
import com.momentus.foundation.ui.metadata.model.ListMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class MetadataDTOHelper {

    @Autowired
    GeneralMessages generalMessages;
    public ListMetadataDTO makeListMetadataDTO(ListMetadata listMetadata , Locale locale)
    {
        ListMetadataDTO listMetadataDTO = new ListMetadataDTO();
         if (listMetadata != null   ){
             listMetadataDTO.setEntity(listMetadata.getEntity());
             if(!CollectionUtils.isEmpty(listMetadata.getListColumns())) {
                 List<ListColumnDTO> listColumnDTOS = new ArrayList<>();
                 for (ListColumn listColumn : listMetadata.getListColumns()) {
                     ListColumnDTO listColumnDTO = new ListColumnDTO(listColumn.getFieldKey(),
                             generalMessages.getMessage(listColumn.getFieldKey(), locale),listColumn.getAccessor());
                     listColumnDTOS.add(listColumnDTO);
                 }
                 listMetadataDTO.setListColumns(listColumnDTOS);
             }
             if(!CollectionUtils.isEmpty(listMetadata.getFilterFields())) {
                 List<FilterFieldDTO> filterFieldDTOS = new ArrayList<>();
                 for(FilterField filterField : listMetadata.getFilterFields()){
                     FilterFieldDTO filterFieldDTO =new FilterFieldDTO(filterField.getFieldKey(),
                             generalMessages.getMessage(filterField.getFieldKey(), locale), filterField.getControl(), filterField.getParam(),
                             filterField.getAccessor());
                     filterFieldDTOS.add(filterFieldDTO);
                 }
                 listMetadataDTO.setFilterFields(filterFieldDTOS);
             }
         }
         return listMetadataDTO;

    }

}


