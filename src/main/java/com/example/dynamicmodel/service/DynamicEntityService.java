package com.example.dynamicmodel.service;

import com.example.dynamicmodel.model.DynamicEntity;
import com.example.dynamicmodel.repository.DynamicEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DynamicEntityService {

    private final DynamicEntityRepository repository;

    public DynamicEntityService(DynamicEntityRepository repository) {
        this.repository = repository;
    }

    public DynamicEntity create(String type, java.util.Map<String, Object> attributes) {
        DynamicEntity entity = new DynamicEntity(type, attributes);
        return repository.save(entity);
    }

    public Optional<DynamicEntity> findById(String id) {
        return repository.findById(id);
    }

    public List<DynamicEntity> findByType(String type) {
        return repository.findAll().stream()
                .filter(e -> type.equals(e.getType()))
                .toList();
    }

    public DynamicEntity update(String id, String type, java.util.Map<String, Object> attributes) {
        DynamicEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found"));
        entity.setType(type);
        entity.setAttributes(attributes);
        return repository.save(entity);
    }

    public boolean delete(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
