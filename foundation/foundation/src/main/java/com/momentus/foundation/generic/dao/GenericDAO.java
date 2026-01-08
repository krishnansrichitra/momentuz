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

    public <T extends BaseEntity> T loadByFilter(Map<String, Object> filter, Class<T> entityClass) {

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

    public <T extends BaseEntity, R> List<R> listFieldByFilter(
            Map<String, Object> filter,
            Class<T> entityClass,
            String field,
            Class<R> fieldClass,
            int offset,
            int limit) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<R> cq = cb.createQuery(fieldClass);
        Root<T> root = cq.from(entityClass);

        List<Predicate> predicates = new ArrayList<>();

        for (Map.Entry<String, Object> entry : filter.entrySet()) {
            if (entry.getValue() == null) continue;

            Path<?> path = root;
            for (String part : entry.getKey().split("\\.")) {
                path = path.get(part);
            }

            if (!entry.getKey().equals(field)){
                predicates.add(cb.equal(path, entry.getValue()));
            }else {
                Expression<String> exp = path.as(String.class);
                predicates.add(cb.like(exp, "%" + entry.getValue().toString() + "%"));
            }
        }

        // resolve selected field
        Path<?> fieldPath = root;
        for (String part : field.split("\\.")) {
            fieldPath = fieldPath.get(part);
        }

        @SuppressWarnings("unchecked")
        Path<R> typedFieldPath = (Path<R>) fieldPath;

        cq.select(typedFieldPath)
                .where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<R> query = em.createQuery(cq);
        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.getResultList();
    }




}
