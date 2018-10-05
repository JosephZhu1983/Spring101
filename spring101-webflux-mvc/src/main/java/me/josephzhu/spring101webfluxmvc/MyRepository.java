package me.josephzhu.spring101webfluxmvc;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyRepository extends MongoRepository<MyData, String> { }