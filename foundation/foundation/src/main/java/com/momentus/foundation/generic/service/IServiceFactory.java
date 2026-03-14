package com.momentus.foundation.generic.service;

import com.momentus.foundation.ui.lookup.service.LookupService;

public interface IServiceFactory {

  public GenericService getService(String entity);

  public LookupService getLookupService(String entity);
}
