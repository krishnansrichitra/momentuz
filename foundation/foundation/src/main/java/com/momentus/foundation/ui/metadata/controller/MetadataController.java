package com.momentus.foundation.ui.metadata.controller;

import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.context.ApplicationContextHelper;
import com.momentus.foundation.ui.metadata.dto.ListMetadataDTO;
import com.momentus.foundation.ui.metadata.dto.UpdateViewMetadataDTO;
import com.momentus.foundation.ui.metadata.service.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/metadata")
public class MetadataController {

  @Autowired ApplicationContextHelper applicationContextHelper;

  @Autowired MetadataService metadataService;

  @GetMapping("/getListMetadata")
  public ResponseEntity<ListMetadataDTO> getListMetadata(
      Authentication authentication, @RequestParam String entity) {
    ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
    ListMetadataDTO set =
        metadataService.getListMetadata(
            context.getOrganization().getId(), entity, context.getLocale());
    return ResponseEntity.ok(set);
  }

  @GetMapping("/getUpdateViewMetadata")
  public ResponseEntity<UpdateViewMetadataDTO> getUpdateViewMetadata(
      Authentication authentication, @RequestParam String entity) {
    ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
    UpdateViewMetadataDTO set =
        metadataService.getUpdateViewMetadata(
            context.getOrganization().getId(), entity, context.getLocale());
    return ResponseEntity.ok(set);
  }
}
