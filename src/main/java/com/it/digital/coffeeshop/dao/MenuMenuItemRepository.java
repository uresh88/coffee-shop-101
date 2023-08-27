package com.it.digital.coffeeshop.dao;

import com.it.digital.coffeeshop.dao.models.MenuItem;
import com.it.digital.coffeeshop.dao.models.MenuMenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface MenuMenuItemRepository extends JpaRepository<MenuMenuItem, Long> {
    public List<MenuMenuItem> findAllByMenuItemIn(Collection<MenuItem> menuItem);
}
