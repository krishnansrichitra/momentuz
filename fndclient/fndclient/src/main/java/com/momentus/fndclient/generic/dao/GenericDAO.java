package com.momentus.fndclient.generic.dao;

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

}
