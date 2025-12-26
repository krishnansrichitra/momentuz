package com.momentus.foundation.generic.dao;


import com.momentus.foundation.common.model.BaseEntity;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public BaseEntity loadById(Class<?extends OrgBasedEntity > entityClass, Long id)
    {
        return em.find(entityClass,id);

    }

    public <T extends BaseEntity> T loadByBK(Map<String, Object> filter, Class<T> entityClass) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, Object> entry : filter.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();
            if (value == null) continue;
            Path<?> path = root;
            for (String part : fieldName.split("\\.")) {
                path = path.get(part);
            }

            predicates.add(cb.equal(path, value));
        }
        cq.select(root)
                .where(cb.and(predicates.toArray(new Predicate[0])));
        TypedQuery<T> query = em.createQuery(cq);
        List<T> results = query.setMaxResults(1).getResultList();
        return results.isEmpty() ? null : results.get(0);
    }


    public <T extends BaseEntity> long getCountForList(Map<String, Object> filter, Class<T> entityClass) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> root = cq.from(entityClass);
        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, Object> entry : filter.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();
            if (value == null) continue;
            Path<?> path = root;
            for (String part : fieldName.split("\\.")) {
                path = path.get(part);
            }

            predicates.add(cb.equal(path, value));
        }
        cq.select(cb.count(root))
                .where(cb.and(predicates.toArray(new Predicate[0])));

        return em.createQuery(cq).getSingleResult();
    }


    public <T extends BaseEntity> List<T> listByFilter(Map<String, Object> filter, Class<T> entityClass,int offset,
                                                    int limit) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, Object> entry : filter.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();
            if (value == null) continue;
            Path<?> path = root;
            for (String part : fieldName.split("\\.")) {
                path = path.get(part);
            }
            predicates.add(cb.equal(path, value));
        }
        cq.select(root)
                .where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<T> query = em.createQuery(cq);

        query.setFirstResult(offset);  // starting index (0-based)
        query.setMaxResults(limit);    // page size

        return query.getResultList();
    }


}
