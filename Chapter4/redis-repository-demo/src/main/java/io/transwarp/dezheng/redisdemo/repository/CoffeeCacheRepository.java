package io.transwarp.dezheng.redisdemo.repository;

import io.transwarp.dezheng.redisdemo.model.CoffeeCache;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CoffeeCacheRepository extends CrudRepository<CoffeeCache, Long> {
  Optional<CoffeeCache> findOneByName(String name);
}
