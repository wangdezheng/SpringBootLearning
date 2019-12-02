package io.transwarp.dezheng.cacheredisdemo.repository;

import io.transwarp.dezheng.cacheredisdemo.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
