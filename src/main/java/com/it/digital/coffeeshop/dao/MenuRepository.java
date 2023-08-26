package com.it.digital.coffeeshop.dao;

import com.it.digital.coffeeshop.dao.models.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
