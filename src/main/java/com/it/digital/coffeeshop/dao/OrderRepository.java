package com.it.digital.coffeeshop.dao;

import com.it.digital.coffeeshop.dao.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserIdAndIsActive(Long userId, boolean isActive);

    List<Order> findAllByIsActive(boolean isActive);
}
