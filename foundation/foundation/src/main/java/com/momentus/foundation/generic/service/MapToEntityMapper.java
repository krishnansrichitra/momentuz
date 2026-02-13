package com.momentus.foundation.generic.service;

import com.momentus.corefw.data.EntityProperties;
import com.momentus.foundation.common.Utils;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.model.Address;
import com.momentus.foundation.common.model.BaseEntity;
import com.momentus.foundation.finitevalue.model.FiniteValue;
import com.momentus.foundation.finitevalue.service.FiniteValueService;
import com.momentus.foundation.generic.controller.GenericController;
import com.momentus.foundation.generic.dao.GenericDAO;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MapToEntityMapper {

  @Autowired GenericDAO genericDAO;

  @Autowired FiniteValueService finiteValueService;

  private static final Logger log = LoggerFactory.getLogger(GenericController.class);

  public Map<String, Object> converToMapFromEntity(BaseEntity source, boolean loadOnlyBK) {
    Map<String, Object> result = new HashMap<>();

    if (source == null) {
      return result;
    }

    Class<?> clazz = source.getClass();

    Field[] fields = clazz.getDeclaredFields();

    for (Field field : fields) {
      try {
        EntityProperties annotation = field.getAnnotation(EntityProperties.class);
        if (!loadOnlyBK || (annotation != null && annotation.isBK())) {
          field.setAccessible(true); // allows access to private fields
          Object value = field.get(source);
          if (FiniteValue.class.isAssignableFrom(field.getType())) {
            if (value != null) {
              result.put(field.getName() + ".fvCode", ((FiniteValue) value).getFvCode());
              result.put(field.getName() + ".fvValue", ((FiniteValue) value).getFvValue());
            } else {
              result.put(field.getName() + ".fvCode", "");
              result.put(field.getName() + ".fvValue", "");
            }
          } else if (Address.class.isAssignableFrom(field.getType())) {
            Address address = (Address) value;
            result.put(field.getName() + ".address1", address != null ? address.getAddress1() : "");
            result.put(field.getName() + ".address2", address != null ? address.getAddress2() : "");
            result.put(field.getName() + ".city", address != null ? address.getCity() : "");
            result.put(field.getName() + ".state", address != null ? address.getState() : "");
            result.put(field.getName() + ".country", address != null ? address.getCountry() : "");
            result.put(field.getName() + ".zipcode", address != null ? address.getZipcode() : "");
            result.put(
                field.getName() + ".phoneNumber", address != null ? address.getPhoneNumber() : "");

          } else if (BaseEntity.class.isAssignableFrom(field.getType()) && value != null) {
            Map<String, Object> mapValue = converToMapFromEntity((BaseEntity) unproxy(value), true);
            for (Map.Entry<String, Object> val : mapValue.entrySet()) {
              result.put(field.getName() + "." + val.getKey(), val.getValue());
            }

          } else {
            result.put(field.getName(), value);
          }
        }
      } catch (IllegalAccessException e) {
        e.printStackTrace();
        log.error("Error on converting to Map", e);
      }
    }
    /**
     * result.put("version",source.getVersion()); result.put("id",source.getPK());
     *
     * <p>To be decided if editing should be allowed
     */
    return result;
  }

  private static Object unproxy(Object entity) {
    if (entity instanceof HibernateProxy) {
      return ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
    }
    return entity;
  }

  public void populateFromMap(
      Map<String, Object> source, BaseEntity target, ApplicationContext context) {
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

        if (Address.class.equals(fieldType) && value instanceof Map) {
          Address address = new Address();
          Map<String, Object> addressValue = (Map) value;
          address.setAddress1(Utils.ConvertToString(addressValue.get("address1")));
          address.setAddress2(Utils.ConvertToString(addressValue.get("address2")));
          address.setCity(Utils.ConvertToString(addressValue.get("city")));
          address.setState(Utils.ConvertToString(addressValue.get("state")));
          address.setCountry(Utils.ConvertToString(addressValue.get("country")));
          address.setZipcode(Utils.ConvertToString(addressValue.get("zipcode")));
          address.setPhoneNumber(Utils.ConvertToString(addressValue.get("phoneNumber")));
          field.set(target, address);
        } else if (isSimpleType(fieldType)) { // Primitive / simple types
          field.set(target, convertValue(value, fieldType));
        } else if (FiniteValue.class.equals(fieldType)) {
          String fvCode = Utils.ConvertToString(((Map) value).get("fvCode"));
          if (fvCode != null) {
            FiniteValue finiteValue = finiteValueService.getFinitieValueByCode(fvCode);
            field.set(target, finiteValue);
          }
        } else if (value instanceof Map) {
          BaseEntity nestedObject = (BaseEntity) fieldType.getDeclaredConstructor().newInstance();
          populateFromMap((Map<String, Object>) value, nestedObject, context);
          if (nestedObject.getPK() != null && nestedObject.getVersion() == null) {
            nestedObject = loadFullObject(nestedObject);
            if (nestedObject == null) {
              throw new RuntimeException(" object not found ");
            }
            field.set(target, nestedObject);

          } else if (nestedObject.getPK() == null
              && nestedObject.getBK() != null
              && OrgBasedEntity.class.isAssignableFrom(nestedObject.getClass())) {
            nestedObject = loadFullObjectByBK((OrgBasedEntity) nestedObject, context);
            if (nestedObject == null) {
              throw new RuntimeException(" object not found ");
            }
            field.set(target, nestedObject);
          }
        } else if (value instanceof List) {
          List list = (List) value;
          Type genericType = field.getGenericType();
          Class<?> elementType = null;
          if (genericType instanceof ParameterizedType pt) {
            Type[] typeArgs = pt.getActualTypeArguments();
            if (typeArgs.length == 1 && typeArgs[0] instanceof Class<?>) {
              elementType = (Class<?>) typeArgs[0];
              // elementType == Integer.class
            }
          }
          List retValue;
          if (field.get(target) == null) retValue = new ArrayList();
          else retValue = (List) field.get(target);

          for (int i = 0; i < list.size(); i++) {
            Object vs = list.get(i);

            if (elementType != null
                && BaseEntity.class.isAssignableFrom(elementType)
                && vs instanceof Map) {
              BaseEntity nestedObject;
              if (field.get(target) == null) nestedObject = (BaseEntity) elementType.newInstance();
              else {
                Object targField = field.get(target);
                if (targField instanceof List && ((List) targField).size() > i) {
                  nestedObject = (BaseEntity) ((List) targField).get(i);
                } else {
                  nestedObject = (BaseEntity) elementType.newInstance();
                }
              }
              populateFromMap((Map<String, Object>) vs, nestedObject, context);
              nestedObject.setParentObject(target);
              if (retValue.size() > i) retValue.set(i, nestedObject);
              else retValue.add(nestedObject);
            }
          }
          field.set(target, retValue);
        }

      } catch (Exception e) {
        throw new RuntimeException(
            "Failed to set field '" + fieldName + "' on " + clazz.getSimpleName(), e);
      }
    }
  }

  private BaseEntity loadFullObject(BaseEntity baseEntity) {
    return genericDAO.loadById(baseEntity);
  }

  private OrgBasedEntity loadFullObjectByBK(
      OrgBasedEntity basedEntity, ApplicationContext context) {
    Map<String, Object> filter = basedEntity.getBK();
    filter.put("orgId.id", context.getOrganization().getId());
    filter.put("deleted", false);
    return genericDAO.loadByFilter(filter, basedEntity.getClass());
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
    if (value == null || !StringUtils.hasLength(Utils.ConvertToString(value))) return null;

    if (targetType.equals(String.class)) {
      return value.toString();
    }
    if (targetType.equals(Long.class)) {
      return value instanceof Number
          ? ((Number) value).longValue()
          : Long.valueOf(value.toString());
    }
    if (targetType.equals(Integer.class)) {
      return value instanceof Number
          ? ((Number) value).intValue()
          : Integer.valueOf(value.toString());
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

  /** Finds field in class or any superclass */
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
