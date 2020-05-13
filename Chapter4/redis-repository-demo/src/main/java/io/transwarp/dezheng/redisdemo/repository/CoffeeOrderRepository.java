package io.transwarp.dezheng.redisdemo.repository;

import io.transwarp.dezheng.redisdemo.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {}
