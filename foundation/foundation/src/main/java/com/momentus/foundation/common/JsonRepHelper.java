package com.momentus.foundation.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.momentus.foundation.common.model.BaseEntity;

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
        return mapper.convertValue(entity,new TypeReference<Map<String, Object>>() {});
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
