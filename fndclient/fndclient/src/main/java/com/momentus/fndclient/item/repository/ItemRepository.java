package com.momentus.fndclient.item.repository;

import com.momentus.fndclient.item.model.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

  public List<Item> findByBarcodeAndOrgId_IdAndDeleted(String barcode, Long orgId, Boolean deleted);
}
