package com.example.pos_mongo.repository;

import com.example.pos_mongo.entity.CatalogDisciplina;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends MongoRepository<CatalogDisciplina, String> {
    CatalogDisciplina findById(ObjectId id);
}
