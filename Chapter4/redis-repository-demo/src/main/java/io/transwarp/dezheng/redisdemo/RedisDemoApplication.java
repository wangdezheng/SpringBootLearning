package io.transwarp.dezheng.redisdemo;

import io.lettuce.core.ReadFrom;
import io.transwarp.dezheng.redisdemo.converter.BytesToMoneyConverter;
import io.transwarp.dezheng.redisdemo.converter.MoneyToBytesConverter;
import io.transwarp.dezheng.redisdemo.model.Coffee;
import io.transwarp.dezheng.redisdemo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@EnableTransactionManagement
@EnableJpaRepositories
@SpringBootApplication
@EnableRedisRepositories
public class RedisDemoApplication implements ApplicationRunner {
  @Autowired private CoffeeService coffeeService;

  public static void main(String[] args) {
    SpringApplication.run(RedisDemoApplication.class, args);
  }

  @Bean
  public RedisCustomConversions redisCustomConversions() {
    return new RedisCustomConversions(
        Arrays.asList(new BytesToMoneyConverter(), new MoneyToBytesConverter()));
  }

  @Bean
  public LettuceClientConfigurationBuilderCustomizer customizer() {
    return builder -> builder.readFrom(ReadFrom.MASTER_PREFERRED);
  }

  @Override
  public void run(ApplicationArguments arguments) throws Exception {
    Optional<Coffee> c = coffeeService.findSimpleCoffeeFromCache("mocha");
    log.info("Coffee is {}", c);

    for (int i = 0; i < 5; i++) {
      c = coffeeService.findSimpleCoffeeFromCache("mocha");
    }
    log.info("Value from Redis: {}", c);
  }
}
