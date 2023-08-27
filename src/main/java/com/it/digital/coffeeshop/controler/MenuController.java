package com.it.digital.coffeeshop.controler;

import com.it.digital.coffeeshop.exception.NotFoundException;
import com.it.digital.coffeeshop.model.dto.MenuDetailsDto;
import com.it.digital.coffeeshop.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/menu", produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {
    @Autowired
    MenuService menuService;

    @PutMapping
    public ResponseEntity createMenu(@RequestBody MenuDetailsDto menuDetailsDto) {
        this.menuService.createNewMenu(menuDetailsDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{menuId}")
    public ResponseEntity<MenuDetailsDto> getMenu(@PathVariable Long menuId) throws NotFoundException {
        MenuDetailsDto menuDetailsDto1 = this.menuService.getMenuDetailsDtoById(menuId);
        return ResponseEntity.ok().body(menuDetailsDto1);

    }

    @PostMapping
    public ResponseEntity<MenuDetailsDto> updateMenu(@RequestBody MenuDetailsDto menuDetailsDto) throws NotFoundException {
        this.menuService.updateMenu(menuDetailsDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{menuId}")
    public ResponseEntity deleteMenu(@PathVariable Long menuId) {
        this.menuService.deleteMenu(menuId);
        return ResponseEntity.ok().build();

    }

}
