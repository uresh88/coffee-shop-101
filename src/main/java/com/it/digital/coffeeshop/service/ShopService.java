package com.it.digital.coffeeshop.service;

import com.it.digital.coffeeshop.exception.NotFoundException;
import com.it.digital.coffeeshop.model.dto.ShopDetailDto;

import java.util.List;

public interface ShopService {

    void createShop(ShopDetailDto shopDetailDto);

    List<ShopDetailDto> getAllShops();

    ShopDetailDto getShopByyId(Long shopId) throws NotFoundException;

    void updateShopById(ShopDetailDto shopDetailDto) throws NotFoundException;

    List<ShopDetailDto> getAllInsideRadius(Long lon, Long lat, Long rad);

    void registerMenuItem(Long shopId, Long menuId) throws NotFoundException;
}
