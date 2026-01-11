package com.momentus.foundation.entity.service;

import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.entity.model.Entity;
import com.momentus.foundation.entity.repository.EntityRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class EntityService {
  @Autowired EntityRepository entityRepository;

  @Autowired GeneralMessages generalMessages;

  @Cacheable("entityPackage")
  public String getFullPackage(String entityName) {
    Entity entity = entityRepository.findById(entityName).orElse(null);
    if (entity == null) return entityName;
    else return entity.getFullPackage();
  }

  @Cacheable("entities")
  public Map<String, String> getAllEntities(Locale locale) {
    Map<String, String> result = new HashMap<>();
    List<Entity> entityList = entityRepository.findAll();
    for (Entity entity : entityList) {
      result.put(
          entity.getEntityName(), generalMessages.getMessage(entity.getEntityName(), locale));
    }
    return result;
  }
}
