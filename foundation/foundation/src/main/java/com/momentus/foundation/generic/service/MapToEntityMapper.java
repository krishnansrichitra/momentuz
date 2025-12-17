package com.momentus.foundation.generic.service;

import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.model.BaseEntity;
import com.momentus.foundation.generic.dao.GenericDAO;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class MapToEntityMapper {

    @Autowired
    GenericDAO genericDAO;


    public  void populateFromMap(Map<String, Object> source, BaseEntity target, ApplicationContext context) {
        if (source == null || target == null) return;

        Class<?> clazz = target.getClass();

        for (Map.Entry<String, Object> entry : source.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();

            Field field = getField(clazz, fieldName);
            if (field == null || value == null) {
                continue;
            }

            try {
                field.setAccessible(true);
                Class<?> fieldType = field.getType();

                // Primitive / simple types
                if (isSimpleType(fieldType)) {
                    field.set(target, convertValue(value, fieldType));
                }
                // Nested object (recursive)
                else if (value instanceof Map) {
                    BaseEntity nestedObject = (BaseEntity) field.get(target);

                    if (nestedObject == null) {
                        nestedObject = (BaseEntity)fieldType.getDeclaredConstructor().newInstance();
                        field.set(target, nestedObject);
                    }

                    populateFromMap((Map<String, Object>) value, nestedObject,context);
                    if (nestedObject.getPK() != null && nestedObject.getVersion() ==null)
                    {
                        nestedObject = loadFullObject(nestedObject);
                        field.set(target,nestedObject);

                    }else if(nestedObject.getPK() == null && nestedObject.getBK() != null && OrgBasedEntity.class.isAssignableFrom(nestedObject.getClass()))  {
                        nestedObject = loadFullObjectByBK((OrgBasedEntity) nestedObject,context);
                        field.set(target,nestedObject);
                    }
                }

            } catch (Exception e) {
                throw new RuntimeException(
                        "Failed to set field '" + fieldName + "' on " + clazz.getSimpleName(), e
                );
            }
        }
    }

    private BaseEntity loadFullObject(BaseEntity baseEntity)
    {
        return genericDAO.loadById(baseEntity);
    }

    private OrgBasedEntity loadFullObjectByBK(OrgBasedEntity basedEntity,ApplicationContext context)
    {
        Map<String,Object> filter = basedEntity.getBK();
        filter.put("orgId.id",context.getOrganization().getId());
        filter.put("deleted",false);
        return genericDAO.loadByBK(filter,basedEntity.getClass());
    }
    // ----------------- Helpers -----------------

    private static boolean isSimpleType(Class<?> type) {
        return type.equals(String.class)
                || type.equals(Long.class)
                || type.equals(Integer.class)
                || type.equals(BigDecimal.class)
                || type.equals(LocalDate.class)
                || type.equals(LocalDateTime.class);
    }

    private static Object convertValue(Object value, Class<?> targetType) {
        if (value == null) return null;

        if (targetType.equals(String.class)) {
            return value.toString();
        }
        if (targetType.equals(Long.class)) {
            return value instanceof Number ? ((Number) value).longValue() : Long.valueOf(value.toString());
        }
        if (targetType.equals(Integer.class)) {
            return value instanceof Number ? ((Number) value).intValue() : Integer.valueOf(value.toString());
        }
        if (targetType.equals(BigDecimal.class)) {
            return value instanceof BigDecimal ? value : new BigDecimal(value.toString());
        }
        if (targetType.equals(LocalDate.class)) {
            return LocalDate.parse(value.toString());
        }
        if (targetType.equals(LocalDateTime.class)) {
            return LocalDateTime.parse(value.toString());
        }

        return value;
    }

    /**
     * Finds field in class or any superclass
     */
    private static Field getField(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ignored) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }
}
