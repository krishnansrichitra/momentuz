package com.momentus.foundation.common.controller;

import com.momentus.foundation.common.JsonRepHelper;
import com.momentus.foundation.common.model.BaseEntity;
import com.momentus.foundation.entity.service.EntityService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meta")
public class MetaController {

  @Autowired EntityService entityService;

  @GetMapping("/getFullMap")
  public ResponseEntity<Map<String, Object>> getFullMap(@RequestParam String entity) {

    try {
      String fullPackage = entityService.getFullPackage(entity);
      BaseEntity obj = (BaseEntity) Class.forName(fullPackage).newInstance();
      return ResponseEntity.ok(JsonRepHelper.getFullMapRepresentation(obj));
    } catch (Exception ex) {
      return ResponseEntity.badRequest().body(new HashMap<>());
    }
  }

  @GetMapping("/getMinimizedMap")
  public ResponseEntity<Map<String, Object>> getMinimizedMap(@RequestParam String entity) {

    try {
      String fullPackage = entityService.getFullPackage(entity);
      BaseEntity obj = (BaseEntity) Class.forName(fullPackage).newInstance();
      return ResponseEntity.ok(JsonRepHelper.getMapRepresentationWithKeys(obj, false));
    } catch (Exception ex) {
      return ResponseEntity.badRequest().body(new HashMap<>());
    }
  }
}
