package com.momentus.foundation.generic.service;

import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.model.Address;
import com.momentus.foundation.common.model.BaseEntity;
import com.momentus.foundation.finitevalue.model.FiniteValue;
import com.momentus.foundation.finitevalue.service.FiniteValueService;
import com.momentus.foundation.generic.dao.GenericDAO;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class MapToEntityMapper {

    @Autowired
    GenericDAO genericDAO;

    @Autowired
    FiniteValueService finiteValueService;


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

                if(Address.class.equals(fieldType) && value instanceof Map) {
                    Address address = new Address();
                    Map<String, Object>addressValue =(Map)value;
                    address.setAddress1(String.valueOf(addressValue.get("address1")));
                    address.setAddress2(String.valueOf(addressValue.get("address2")));
                    address.setCity(String.valueOf(addressValue.get("city")));
                    address.setState(String.valueOf(addressValue.get("state")));
                    address.setCountry(String.valueOf(addressValue.get("country")));
                    address.setZipcode(String.valueOf(addressValue.get("zipcode")));
                    address.setPhoneNumber(String.valueOf(addressValue.get("phoneNumber")));
                    field.set(target,address);
                } else if (isSimpleType(fieldType)) { // Primitive / simple types
                    field.set(target, convertValue(value, fieldType));
                }else if (FiniteValue.class.equals(fieldType) ) {
                   String fvCode = String.valueOf(((Map) value).get("fvCode"));
                   FiniteValue finiteValue =  finiteValueService.getFinitieValueByCode(fvCode);
                   field.set(target,finiteValue);
                } else if (value instanceof Map) {
                    BaseEntity nestedObject = (BaseEntity)fieldType.getDeclaredConstructor().newInstance();
                    populateFromMap((Map<String, Object>) value, nestedObject,context);
                    if (nestedObject.getPK() != null && nestedObject.getVersion() ==null)
                    {
                        nestedObject = loadFullObject(nestedObject);
                        if (nestedObject == null) {
                            throw new RuntimeException( " object not found ");
                        }
                        field.set(target,nestedObject);

                    }else if(nestedObject.getPK() == null && nestedObject.getBK() != null && OrgBasedEntity.class.isAssignableFrom(nestedObject.getClass()))  {
                        nestedObject = loadFullObjectByBK((OrgBasedEntity) nestedObject,context);
                        if (nestedObject == null) {
                            throw new RuntimeException( " object not found ");
                        }
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
        return genericDAO.loadByFilter(filter,basedEntity.getClass());
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
