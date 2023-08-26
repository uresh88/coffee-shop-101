package com.it.digital.coffeeshop.service.impl;

import com.it.digital.coffeeshop.dao.ShopRepository;
import com.it.digital.coffeeshop.dao.models.Shop;
import com.it.digital.coffeeshop.exception.NotFoundException;
import com.it.digital.coffeeshop.model.dto.ShopDetailDto;
import com.it.digital.coffeeshop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    ShopRepository shopRepository;

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

    @Override
    public ShopDetailDto getShopByyId(Long shopId) throws NotFoundException {
        Optional<Shop> optShop = this.shopRepository.findById(shopId);
        if (!optShop.isPresent()) {
            throw new NotFoundException("No matching shop");
        }
        Shop shop = optShop.get();
        ShopDetailDto shopDetailDto = this.shopToShopDetailDto(shop);
        return shopDetailDto;
    }

    @Override
    public void updateShopById(ShopDetailDto shopDetailDto) throws NotFoundException {
        Optional<Shop> optionalShop = this.shopRepository.findById(shopDetailDto.getId());
        if (!optionalShop.isPresent()) {
            throw new NotFoundException("Shop not found");
        }
        Shop shop = optionalShop.get();
        shopDetailDtoToShop(shopDetailDto, shop);
        this.shopRepository.saveAndFlush(shop);
    }

    @Override
    public List<ShopDetailDto> getAllInsideRadius(Long lon, Long lat, Long rad) {
        List<Shop> shopList = this.shopRepository.findAllByLonBeforeAndLatBefore(lon + rad, lat + rad);
        List<ShopDetailDto> shopDetailDtoList = shopList.stream().map(this::shopToShopDetailDto).collect(Collectors.toList());
        return shopDetailDtoList;
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
