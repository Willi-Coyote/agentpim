package com.example.dynamicmodel.repository;

import com.example.dynamicmodel.model.DynamicEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DynamicEntityRepository extends MongoRepository<DynamicEntity, String> {
}
