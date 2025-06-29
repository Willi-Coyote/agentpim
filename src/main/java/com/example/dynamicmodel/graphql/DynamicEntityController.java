package com.example.dynamicmodel.graphql;

import com.example.dynamicmodel.model.DynamicEntity;
import com.example.dynamicmodel.service.DynamicEntityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class DynamicEntityController {

    private final DynamicEntityService service;
    private final ObjectMapper mapper = new ObjectMapper();

    public DynamicEntityController(DynamicEntityService service) {
        this.service = service;
    }

    @QueryMapping
    public DynamicEntity entity(@Argument String id, @Argument String type) {
        return service.findById(id)
                .filter(e -> type.equals(e.getType()))
                .orElse(null);
    }

    @QueryMapping
    public java.util.List<DynamicEntity> entities(@Argument String type) {
        return service.findByType(type);
    }

    @MutationMapping
    public DynamicEntity createEntity(@Argument String type, @Argument Map<String, Object> attributes) {
        return service.create(type, attributes);
    }

    @MutationMapping
    public DynamicEntity updateEntity(@Argument String id, @Argument String type, @Argument Map<String, Object> attributes) {
        return service.update(id, type, attributes);
    }

    @MutationMapping
    public Boolean deleteEntity(@Argument String id) {
        return service.delete(id);
    }

    public GraphQLScalarType jsonScalar() {
        return ExtendedScalars.Json;
    }
}
