package com.momentus.foundation.generic.dao;


import com.momentus.foundation.common.model.BaseEntity;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

@Component
public class GenericDAO {

    @PersistenceContext
    protected EntityManager em;


    public void create(OrgBasedEntity model) {
        em.merge(model);
    }

    public BaseEntity loadById(BaseEntity entity)
    {
       return em.find(entity.getClass(),entity.getPK());

    }




}
