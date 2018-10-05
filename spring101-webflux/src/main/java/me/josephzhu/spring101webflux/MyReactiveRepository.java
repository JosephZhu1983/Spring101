package me.josephzhu.spring101webflux;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyReactiveRepository extends ReactiveMongoRepository<MyData, String> { }