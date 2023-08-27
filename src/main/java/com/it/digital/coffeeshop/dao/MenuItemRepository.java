package com.it.digital.coffeeshop.dao;

import com.it.digital.coffeeshop.dao.models.MenuItem;
import com.it.digital.coffeeshop.dao.models.MenuMenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    Set<MenuItem> findAllByIdIn(Collection<Long> idList);
}


