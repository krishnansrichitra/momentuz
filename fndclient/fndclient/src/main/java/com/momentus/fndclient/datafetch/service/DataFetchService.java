package com.momentus.fndclient.datafetch.service;

import com.momentus.fndclient.datafetch.dto.FetchResponseDTO;
import com.momentus.fndclient.item.model.Item;
import com.momentus.fndclient.item.repository.ItemRepository;
import com.momentus.foundation.common.context.ApplicationContext;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class DataFetchService {

  @Autowired ItemRepository itemRepository;

  public FetchResponseDTO getItemByBarCode(ApplicationContext context, String barcode) {
    List<Item> items =
        itemRepository.findByBarcodeAndOrgId_IdAndDeleted(
            barcode, context.getOrganization().getId(), false);
    FetchResponseDTO fetchResponseDTO = new FetchResponseDTO();
    Map<String, Object> props = new LinkedHashMap<>();
    if (!CollectionUtils.isEmpty(items)) {
      Item item = items.get(0);
      props.put("itemName", item.getItemName());
      props.put("price", item.getPrice());
      fetchResponseDTO.setProperties(props);
    }
    return fetchResponseDTO;
  }
}
