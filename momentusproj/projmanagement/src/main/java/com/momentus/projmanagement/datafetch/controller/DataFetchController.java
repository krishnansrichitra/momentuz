package com.momentus.projmanagement.datafetch.controller;

import com.momentus.projmanagement.datafetch.dto.FetchResponseDTO;
import com.momentus.projmanagement.datafetch.service.DataFetchService;
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


}
