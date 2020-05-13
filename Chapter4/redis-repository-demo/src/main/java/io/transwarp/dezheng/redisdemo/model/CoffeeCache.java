package io.transwarp.dezheng.redisdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

@RedisHash(value = "springbucks-coffee", timeToLive = 60)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CoffeeCache {
  @Id private long id;
  @Indexed private String name;
  private Money price;
}
