package io.transwarp.dezheng.redisdemo.service;

import io.transwarp.dezheng.redisdemo.model.Coffee;
import io.transwarp.dezheng.redisdemo.model.CoffeeCache;
import io.transwarp.dezheng.redisdemo.repository.CoffeeCacheRepository;
import io.transwarp.dezheng.redisdemo.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Slf4j
@Service
public class CoffeeService {
  private static final String CACHE = "springbucks-coffee";

  private CoffeeRepository coffeeRepository;
  private CoffeeCacheRepository coffeeCacheRepository;

  public CoffeeService(
      CoffeeRepository coffeeRepository, CoffeeCacheRepository coffeeCacheRepository) {
    this.coffeeRepository = coffeeRepository;
    this.coffeeCacheRepository = coffeeCacheRepository;
  }

  public List<Coffee> findAllCoffee() {
    return coffeeRepository.findAll();
  }

  public Optional<Coffee> findSimpleCoffeeFromCache(String name) {
    Optional<CoffeeCache> cached = coffeeCacheRepository.findOneByName(name);
    if (cached.isPresent()) {
      CoffeeCache coffeeCache = cached.get();
      Coffee coffee =
          Coffee.builder().name(coffeeCache.getName()).price(coffeeCache.getPrice()).build();
      log.info("Coffee {} found in cache", coffeeCache);
      return Optional.of(coffee);
    } else {
      Optional<Coffee> raw = findOneCoffee(name);
      raw.ifPresent(
          c -> {
            CoffeeCache coffeeCache =
                CoffeeCache.builder().id(c.getId()).name(c.getName()).price(c.getPrice()).build();
            log.info("Save Coffee {} to cache", coffeeCache);
            coffeeCacheRepository.save(coffeeCache);
          });
      return raw;
    }
  }

  public Optional<Coffee> findOneCoffee(String name) {
    ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name", exact().ignoreCase());
    Optional<Coffee> coffee =
        coffeeRepository.findOne(Example.of(Coffee.builder().name(name).build(), matcher));
    log.info("Coffee found {}", coffee);
    return coffee;
  }
}
