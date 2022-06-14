package com.netcracker.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.json.JsonMergePatch;
import javax.json.JsonValue;

@Component
public class JsonPatch {

    @Autowired
    ObjectMapper mapper;

    public <T> T mergePatch(JsonMergePatch mergePatch, T targetBean, Class<T> beanClass) {

        // Convert the Java bean to a JSON document
        JsonValue target = mapper.convertValue(targetBean, JsonValue.class);

        // Apply the JSON Merge Patch to the JSON document
        JsonValue patched = mergePatch.apply(target);

        // Convert the JSON document to a Java bean and return it
        return mapper.convertValue(patched, beanClass);
    }
}
