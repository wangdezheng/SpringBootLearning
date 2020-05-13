package io.transwarp.dezheng.redisdemo.service;

import io.transwarp.dezheng.redisdemo.model.Coffee;
import io.transwarp.dezheng.redisdemo.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Slf4j
@Service
public class CoffeeService {
  private static final String CACHE = "springbucks-coffee";

  private CoffeeRepository coffeeRepository;
  private RedisTemplate<String, Coffee> redisTemplate;

  public CoffeeService(
      CoffeeRepository coffeeRepository, RedisTemplate<String, Coffee> redisTemplate) {
    this.coffeeRepository = coffeeRepository;
    this.redisTemplate = redisTemplate;
  }

  public Optional<Coffee> findOneCoffee(String name) {
    HashOperations<String, String, Coffee> hashOperations = redisTemplate.opsForHash();
    if (redisTemplate.hasKey(CACHE) && hashOperations.hasKey(CACHE, name)) {
      log.info("Get coffee {} from Redis.", name);
      return Optional.ofNullable(hashOperations.get(CACHE, name));
    }

    ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name", exact().ignoreCase());
    Optional<Coffee> coffee =
        coffeeRepository.findOne(Example.of(Coffee.builder().name(name).build(), matcher));
    if (coffee.isPresent()) {
      log.info("Put coffee {} to Redis", name);
      hashOperations.put(CACHE, name, coffee.get());
      redisTemplate.expire(CACHE, 1, TimeUnit.MINUTES);
    }
    return coffee;
  }
}
