package com.momentus.foundation.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.momentus.foundation.common.model.BaseEntity;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonRepHelper {

    public static <T extends BaseEntity> String getJSONRepresentation(T entity) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(entity);
        return json;
    }

    public static <T extends BaseEntity>  Map<String,Object> getMapRepresentation(T entity)
    {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = mapper.convertValue(entity,new TypeReference<Map<String, Object>>() {});
        for (String key : map.keySet())
        {
            try {
                Field field = entity.getClass().getDeclaredField(key);
                Class<?> fieldType = field.getType();
                if (BaseEntity.class.isAssignableFrom( fieldType)) {
                    Map<String,Object> childMap = getMapRepresentation((BaseEntity) fieldType.newInstance());
                    map.put(key,childMap);
                }
                if (List.class.isAssignableFrom(fieldType)) {
                    Type type = field.getGenericType();
                    if (type instanceof ParameterizedType parameterizedType) {
                        Type actualType = parameterizedType.getActualTypeArguments()[0];
                        List<Map<String,Object>> childList = new ArrayList<>();
                        Class<?> cls = (Class<?>) actualType;
                        if (BaseEntity.class.isAssignableFrom( cls)) {
                            Map<String,Object> childMap = getMapRepresentation((BaseEntity) cls.newInstance());
                            childList.add(childMap);
                        }
                        map.put(key,childList);
                    }
                }
            }catch ( Exception ex)
            {
                // this can't happen
            }

        }
        return  map ;
    }

    public static <T extends BaseEntity> T getEntityFromJsonString(String json, Class<T> clazz) throws JsonProcessingException, JsonMappingException
    {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json,clazz);

    }

    public static <T extends BaseEntity> T getEntityFromMap(Map<String,Object> map, Class<T> clazz)
    {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(map,clazz);
    }






}
