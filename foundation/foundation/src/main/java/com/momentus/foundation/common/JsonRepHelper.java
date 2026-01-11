package com.momentus.foundation.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.momentus.corefw.data.EntityProperties;
import com.momentus.foundation.common.model.Address;
import com.momentus.foundation.common.model.BaseEntity;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class JsonRepHelper {

  public static <T extends BaseEntity> String getJSONRepresentation(T entity)
      throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    String json = mapper.writeValueAsString(entity);
    return json;
  }

  private static Field getDeclaredField(Class entityClass, String key) {
    try {
      return entityClass.getDeclaredField(key);
    } catch (NoSuchFieldException ex) {
      entityClass = entityClass.getSuperclass();
      return getDeclaredField(entityClass, key);
    }
  }

  public static Map<String, Object> getEntityToMap(Object pojo) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.registerModule(new Hibernate6Module());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    return mapper.convertValue(pojo, new TypeReference<Map<String, Object>>() {});
  }

  public static <T extends BaseEntity> Map<String, Object> getFullMapRepresentation(T entity) {
    if (entity == null) return new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.registerModule(new Hibernate6Module());
    Map<String, Object> map =
        mapper.convertValue(entity, new TypeReference<Map<String, Object>>() {});
    for (String key : map.keySet()) {
      try {
        Field field = getDeclaredField(entity.getClass(), key);
        Class<?> fieldType = field.getType();
        if (BaseEntity.class.isAssignableFrom(fieldType)) {
          Map<String, Object> childMap =
              getFullMapRepresentation((BaseEntity) fieldType.newInstance());
          map.put(key, childMap);
        }
        if (List.class.isAssignableFrom(fieldType)) {
          Type type = field.getGenericType();
          if (type instanceof ParameterizedType parameterizedType) {
            Type actualType = parameterizedType.getActualTypeArguments()[0];
            List<Map<String, Object>> childList = new ArrayList<>();
            Class<?> cls = (Class<?>) actualType;
            if (BaseEntity.class.isAssignableFrom(cls)) {
              Map<String, Object> childMap =
                  getFullMapRepresentation((BaseEntity) cls.newInstance());
              childList.add(childMap);
            }
            map.put(key, childList);
          }
        }
      } catch (Exception ex) {
        entity.getClass().getSuperclass();
        ex.printStackTrace();
        // this can't happen
      }
    }
    return map;
  }

  public static <T extends BaseEntity> T getEntityFromJsonString(String json, Class<T> clazz)
      throws JsonProcessingException, JsonMappingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    return mapper.readValue(json, clazz);
  }

  public static <T extends BaseEntity> T getEntityFromMap(Map<String, Object> map, Class<T> clazz) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.registerModule(new Hibernate6Module());
    return mapper.convertValue(map, clazz);
  }

  public static <T extends BaseEntity> Map<String, Object> getMapRepresentationWithKeys(
      T entity, Boolean subObject) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.registerModule(new Hibernate6Module());
    Map<String, Object> map =
        mapper.convertValue(entity, new TypeReference<Map<String, Object>>() {});
    Map<String, Object> newMap = new LinkedHashMap<>();
    Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
    while (iterator.hasNext()) {
      try {
        Map.Entry<String, Object> entry = iterator.next();
        String key = entry.getKey();
        Object value = entry.getValue();
        Field field = getDeclaredField(entity.getClass(), key);
        Class<?> fieldType = field.getType();
        if (subObject == true) {
          EntityProperties annotation = field.getAnnotation(EntityProperties.class);
          if (annotation != null && (annotation.isBK() || annotation.isPK())) {
            newMap.put(key, value);
          }
        } else {
          if (BaseEntity.class.isAssignableFrom(fieldType)) {
            Map<String, Object> childMap =
                getMapRepresentationWithKeys((BaseEntity) fieldType.newInstance(), true);
            newMap.put(key, childMap);
          } else if (Address.class.isAssignableFrom(fieldType)) {
            // Map<String, Object> childMap = getMapRepresentationWithKeys((BaseEntity)
            // fieldType.newInstance(), true);
            Map<String, Object> childMap =
                mapper.convertValue(new Address(), new TypeReference<Map<String, Object>>() {});
            newMap.put(key, childMap);
          } else if (List.class.isAssignableFrom(fieldType)) {
            Type type = field.getGenericType();
            if (type instanceof ParameterizedType parameterizedType) {
              Type actualType = parameterizedType.getActualTypeArguments()[0];
              List<Map<String, Object>> childList = new ArrayList<>();
              Class<?> cls = (Class<?>) actualType;
              if (BaseEntity.class.isAssignableFrom(cls)) {
                Map<String, Object> childMap =
                    getMapRepresentationWithKeys((BaseEntity) cls.newInstance(), false);
                childList.add(childMap);
              }
              newMap.put(key, childList);
            }
          } else {
            newMap.put(key, entry.getValue());
          }
        }
      } catch (Exception ex) {
        entity.getClass().getSuperclass();
        ex.printStackTrace();
        // this can't happen
      }
    }
    return newMap;
  }
}
