package io.transwarp.dezheng.redisdemo.repository;

import io.transwarp.dezheng.redisdemo.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {}
