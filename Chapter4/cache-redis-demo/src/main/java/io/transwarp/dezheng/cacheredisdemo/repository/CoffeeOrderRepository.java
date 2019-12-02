package io.transwarp.dezheng.cacheredisdemo.repository;

import io.transwarp.dezheng.cacheredisdemo.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
