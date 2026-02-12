package com.momentus.fndclient.datafetch.controller;

import com.momentus.fndclient.datafetch.dto.FetchResponseDTO;
import com.momentus.fndclient.datafetch.service.DataFetchService;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.context.ApplicationContextHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/datafetch")
public class DataFetchController {

  @Autowired ApplicationContextHelper applicationContextHelper;

  private static final Logger log = LoggerFactory.getLogger(DataFetchController.class);

  @Autowired DataFetchService dataFetchService;

  @GetMapping({"itemByBarcode"})
  public ResponseEntity<FetchResponseDTO> itemByBarcode(
      @RequestParam String barcode, Authentication authentication) {
    log.debug("itemByBarcode " + barcode);
    ApplicationContext context = this.applicationContextHelper.generateAppContext(authentication);
    FetchResponseDTO fetchResponseDTO = dataFetchService.getItemByBarCode(context, barcode);
    return ResponseEntity.ok(fetchResponseDTO);
  }
}
