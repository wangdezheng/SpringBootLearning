package io.transwarp.dezheng.mongodbdemo;

import com.mongodb.client.result.UpdateResult;
import io.transwarp.dezheng.mongodbdemo.converter.MoneyReadConverter;
import io.transwarp.dezheng.mongodbdemo.model.Coffee;
import io.transwarp.dezheng.mongodbdemo.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@SpringBootApplication
@Slf4j
@EnableMongoRepositories
public class MongodbDemoApplication implements ApplicationRunner {

  @Autowired private MongoTemplate mongoTemplate;

  @Autowired private CoffeeRepository coffeeRepository;

  public static void main(String[] args) {
    SpringApplication.run(MongodbDemoApplication.class, args);
  }

  @Bean
  public MongoCustomConversions mongoCustomConversions() {
    return new MongoCustomConversions(Arrays.asList(new MoneyReadConverter()));
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    Coffee expresso =
        Coffee.builder()
            .name("expresso")
            .price(Money.of(CurrencyUnit.of("CNY"), 20.0))
            .createTime(new Date())
            .updateTime(new Date())
            .build();
    Coffee latte =
        Coffee.builder()
            .name("latte")
            .price(Money.of(CurrencyUnit.of("CNY"), 30.0))
            .createTime(new Date())
            .updateTime(new Date())
            .build();
    coffeeRepository.insert(Arrays.asList(expresso, latte));
    coffeeRepository.findAll(Sort.by("name"))
			.forEach(c -> log.info("Saved Coffee {}", c));

    Thread.sleep(1000);
    latte.setPrice(Money.of(CurrencyUnit.of("CNY"), 35.0));
    latte.setUpdateTime(new Date());
    coffeeRepository.save(latte);
    coffeeRepository.findByName("latte")
			.forEach(c -> log.info("Coffee {}", c));

    coffeeRepository.deleteAll();

//    Coffee expresso =
//        Coffee.builder()
//            .name("expresso")
//            .price(Money.of(CurrencyUnit.of("CNY"), 20.0))
//            .createTime(new Date())
//            .updateTime(new Date())
//            .build();
//    Coffee saved = mongoTemplate.save(expresso);
//    log.info("Coffee {}", saved);
//
//    List<Coffee> list = mongoTemplate.find(query(where("name").is("expresso")), Coffee.class);
//    log.info("Find {} Coffee", list.size());
//    list.forEach(c -> log.info("Coffee: {}", c));
//
//    Thread.sleep(1000);
//    UpdateResult result =
//        mongoTemplate.updateFirst(
//            query(where("name").is("expresso")),
//            new Update()
//                .set("price", Money.of(CurrencyUnit.of("CNY"), 30.0))
//                .currentDate("updateTime"),
//            Coffee.class);
//    log.info("Update Result: {}", result.getModifiedCount());
//    Coffee updateOne = mongoTemplate.findById(saved.getId(), Coffee.class);
//    log.info("Update Result: {}", updateOne);
//
//    mongoTemplate.remove(updateOne);
  }
}
