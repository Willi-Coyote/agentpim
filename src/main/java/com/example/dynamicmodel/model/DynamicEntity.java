package com.example.dynamicmodel.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Document(collection = "entities")
public class DynamicEntity {
    @Id
    private String id;
    private String type;
    private Map<String, Object> attributes = new HashMap<>();

    public DynamicEntity() {}

    public DynamicEntity(String type, Map<String, Object> attributes) {
        this.type = type;
        this.attributes = attributes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}
