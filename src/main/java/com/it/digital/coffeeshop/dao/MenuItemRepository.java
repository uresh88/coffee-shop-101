package com.it.digital.coffeeshop.dao;

import com.it.digital.coffeeshop.dao.models.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findAllByIdIn(Collection<Long> idList);
}


