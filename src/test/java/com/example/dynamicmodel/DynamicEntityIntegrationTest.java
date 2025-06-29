package com.example.dynamicmodel;

import com.example.dynamicmodel.model.DynamicEntity;
import com.example.dynamicmodel.repository.DynamicEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@GraphQlTest
class DynamicEntityIntegrationTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private DynamicEntityRepository repository;

    @Test
    void createAndQueryEntity() {
        DynamicEntity saved = new DynamicEntity("Client", Map.of("firstname", "John"));
        saved.setId("1");

        when(repository.save(any(DynamicEntity.class))).thenReturn(saved);
        when(repository.findById("1")).thenReturn(java.util.Optional.of(saved));
        when(repository.findAll()).thenReturn(java.util.List.of(saved));

        String mutation = "mutation { createEntity(type: \"Client\", attributes: {firstname: \"John\"}) { id type attributes } }";
        graphQlTester.document(mutation)
                .execute()
                .path("createEntity.type").entity(String.class).isEqualTo("Client");

        String query = "{ entity(id: \"1\", type: \"Client\") { id type attributes } }";
        graphQlTester.document(query)
                .execute()
                .path("entity.id").entity(String.class).isEqualTo("1");

        verify(repository, times(1)).save(any(DynamicEntity.class));
    }
}
