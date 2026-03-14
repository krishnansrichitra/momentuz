package com.momentus.projmanagement.common;

import com.momentus.foundation.generic.service.GenericService;
import com.momentus.foundation.generic.service.IServiceFactory;
import com.momentus.foundation.ui.lookup.service.LookupService;
import com.momentus.projmanagement.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceFactory implements IServiceFactory {

  @Autowired GenericService genericService;

  @Autowired ProjectService projectService;

  @Autowired LookupService lookupService;

  @Autowired
  CustomLookupService customLookupService;


  @Override
  public GenericService getService(String entity) {
    if ("Project".equalsIgnoreCase(entity)) {
      return projectService;
    } else {
      return genericService;
    }
  }


  @Override
  public LookupService getLookupService(String entity) {
      if ("Project".equalsIgnoreCase(entity) ||  "Sprint".equalsIgnoreCase(entity))
          return customLookupService ;
    else

      return lookupService;
  }
}
