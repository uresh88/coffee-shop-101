package com.it.digital.coffeeshop.controler;


import com.it.digital.coffeeshop.model.dto.ShopDetailDto;
import com.it.digital.coffeeshop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/shops", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShopController {
    @Autowired
    private ShopService shopService;

    @PutMapping
    public ResponseEntity createShop(@RequestBody ShopDetailDto shopDetailDto) {
        this.shopService.createShop(shopDetailDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ShopDetailDto>> viewShopList() {
        List<ShopDetailDto> shopDetailDtoList = this.shopService.getAllShops();
        return ResponseEntity.ok().body(shopDetailDtoList);
    }

    @GetMapping(value = "/{shopId}")
    public ResponseEntity<ShopDetailDto> viewShopByyId(@PathVariable Long shopId) throws Exception {
        ShopDetailDto shopDetailDto = this.shopService.getShopByyId(shopId);
        return ResponseEntity.ok().body(shopDetailDto);
    }

    @PostMapping
    public ResponseEntity<ShopDetailDto> updateShop(@RequestBody ShopDetailDto shopDetailDto) throws Exception {
        this.shopService.updateShopById(shopDetailDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/locate/{lon}/{lat}/{rad}")
    public ResponseEntity<List<ShopDetailDto>> listShopsNearBy(@PathVariable Long lon,
                                                               @PathVariable Long lat,
                                                               @PathVariable Long rad) {
        List<ShopDetailDto> userDetailsDtoList = shopService.getAllInsideRadius(lon, lat, rad);
        if (userDetailsDtoList == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(userDetailsDtoList, HttpStatus.OK);
    }
}
