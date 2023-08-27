package com.it.digital.coffeeshop.dao;

import com.it.digital.coffeeshop.dao.models.MenuMenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuMenuItemRepository extends JpaRepository<MenuMenuItem, Long> {
}
