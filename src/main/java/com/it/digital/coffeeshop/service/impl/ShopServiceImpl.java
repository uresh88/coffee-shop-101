package com.it.digital.coffeeshop.service.impl;

import com.it.digital.coffeeshop.dao.ShopRepository;
import com.it.digital.coffeeshop.dao.models.Menu;
import com.it.digital.coffeeshop.dao.models.Shop;
import com.it.digital.coffeeshop.exception.NotFoundException;
import com.it.digital.coffeeshop.model.dto.ShopDetailDto;
import com.it.digital.coffeeshop.service.MenuService;
import com.it.digital.coffeeshop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private MenuService menuService;

    @Override
    public void createShop(ShopDetailDto shopDetailDto) {
        Shop shop = new Shop();
        this.shopDetailDtoToShop(shopDetailDto, shop);
        shopRepository.save(shop);
    }

    @Override
    public List<ShopDetailDto> getAllShops() {
        List<Shop> shopList = this.shopRepository.findAll();
        List<ShopDetailDto> shopDetailDtoList = shopList.stream().map(this::shopToShopDetailDto).collect(Collectors.toList());
        return shopDetailDtoList;
    }

    private Shop getShopById(Long shopId) throws NotFoundException {
        Optional<Shop> optShop = this.shopRepository.findById(shopId);
        if (!optShop.isPresent()) {
            throw new NotFoundException("No matching shop");
        }
        return optShop.get();
    }

    @Override
    public ShopDetailDto getShopByyId(Long shopId) throws NotFoundException {
        Shop shop = this.getShopById(shopId);
        ShopDetailDto shopDetailDto = this.shopToShopDetailDto(shop);
        return shopDetailDto;
    }

    @Override
    public void updateShopById(ShopDetailDto shopDetailDto) throws NotFoundException {
        Shop shop = this.getShopById(shopDetailDto.getId());
        shopDetailDtoToShop(shopDetailDto, shop);
        this.shopRepository.saveAndFlush(shop);
    }

    @Override
    public List<ShopDetailDto> getAllInsideRadius(Long lon, Long lat, Long rad) {
        List<Shop> shopList = this.shopRepository.findAllByLonBeforeAndLatBefore(lon + rad, lat + rad);
        List<ShopDetailDto> shopDetailDtoList = shopList.stream().map(this::shopToShopDetailDto).collect(Collectors.toList());
        return shopDetailDtoList;
    }

    @Override
    public void registerMenuItem(Long shopId, Long menuId) throws NotFoundException {
        //get menu item -- error if not avail
        Menu menu = this.menuService.getMenuById(menuId);
        //get shop for the given id-- error if not avail
        Shop shop = this.getShopById(shopId);
        //save current menuItem to shop
        shop.setMenu(menu);
        this.shopRepository.saveAndFlush(shop);
    }

    private ShopDetailDto shopToShopDetailDto(Shop shop) {
        ShopDetailDto shopDetailDto = ShopDetailDto.builder()
                .name(shop.getName())
                .address(shop.getAddress())
                .mobileNumber(shop.getMobileNumber())
                .lat(shop.getLat())
                .id(shop.getId())
                .lon(shop.getLon()).build();
        return shopDetailDto;
    }

    private void shopDetailDtoToShop(ShopDetailDto shopDetailDto, Shop shop) {
        shop.setName(shopDetailDto.getName());
        shop.setMobileNumber(shopDetailDto.getMobileNumber());
        shop.setAddress(shopDetailDto.getAddress());
        shop.setLon(shopDetailDto.getLon());
        shop.setLat(shopDetailDto.getLat());
    }


}
