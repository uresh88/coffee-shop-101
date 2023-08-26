package com.it.digital.coffeeshop.dao;

import com.it.digital.coffeeshop.dao.models.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    List<Shop> findAllByLonBeforeAndLatBefore(Long lon, Long lat);
}
