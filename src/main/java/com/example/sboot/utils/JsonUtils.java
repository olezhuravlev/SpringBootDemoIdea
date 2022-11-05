package com.example.sboot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.List;

@UtilityClass
public class JsonUtils {

    private static ObjectMapper objectMapper;

    public static void setObjectMapper(ObjectMapper objectMapper) {
        JsonUtils.objectMapper = objectMapper;
    }

    public static <T> List<T> readValues(String json, Class<T> clazz) throws IOException {
        ObjectReader reader = objectMapper.readerFor(clazz);
        return reader.<T>readValues(json).readAll();
    }

    public static <T> T readValue(String json, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(json, clazz);
    }

    public static <T> String writeValue(T obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}
