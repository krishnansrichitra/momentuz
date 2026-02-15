package com.momentus.projmanagement.common;

import com.momentus.foundation.generic.service.GenericService;
import com.momentus.foundation.generic.service.IServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceFactory implements IServiceFactory {

  @Autowired GenericService genericService;


  @Override
  public GenericService getService(String entity) {
    return genericService;
  }
}
