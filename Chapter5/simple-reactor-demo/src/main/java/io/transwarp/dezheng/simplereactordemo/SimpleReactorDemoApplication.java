package io.transwarp.dezheng.simplereactordemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
@Slf4j
public class SimpleReactorDemoApplication implements ApplicationRunner {

  public static void main(String[] args) {
    SpringApplication.run(SimpleReactorDemoApplication.class, args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    Flux.range(1, 6)
        .publishOn(Schedulers.elastic())
        .doOnRequest(n -> log.info("Request {} number", n)) // take care of code order
        .doOnComplete(() -> log.info("Publish COMPLETE 1"))
        .map(
            i -> {
              log.info("Publish {}, {}", Thread.currentThread(), i);
              //              return 10 / (i - 3);
              return i;
            })
        .doOnComplete(() -> log.info("Publish COMPLETE 2"))
        .subscribeOn(Schedulers.single())
        .onErrorResume(
            e -> {
              log.error("Exception: {}", e.toString());
              return Mono.just(-1);
            })
        //        .onErrorReturn(-1)
        .subscribe(
            i -> log.info("Subscribe {}: {}", Thread.currentThread(), i),
            e -> log.error("error {}", e.toString()),
            () -> log.info("Subscribe COMPLETE"),
            r -> r.request(4));
  }
}
