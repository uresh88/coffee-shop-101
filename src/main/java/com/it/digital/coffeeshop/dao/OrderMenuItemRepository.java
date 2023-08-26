package com.it.digital.coffeeshop.dao;

import com.it.digital.coffeeshop.dao.models.OrderMenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMenuItemRepository extends JpaRepository<OrderMenuItem, Long> {
}
