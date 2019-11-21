package io.transwarp.dezheng.mongodbdemo.repository;

import io.transwarp.dezheng.mongodbdemo.model.Coffee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CoffeeRepository extends MongoRepository<Coffee, Long> {
    List<Coffee> findByName(String name);
}
