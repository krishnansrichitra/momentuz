package com.momentus.foundation.entity.service;

import com.momentus.foundation.entity.model.Entity;
import com.momentus.foundation.entity.repository.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityService {
    @Autowired
    EntityRepository entityRepository;

    public String getFullPackage(String entityName)
    {
        Entity entity = entityRepository.findById(entityName).orElse(null);
        if (entity == null)
            return entityName;
        else
            return entity.getFullPackage();
    }
}
