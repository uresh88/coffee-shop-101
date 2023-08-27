package com.it.digital.coffeeshop.service;

import com.it.digital.coffeeshop.model.dto.MenuDetailsDto;

public interface MenuService {
    void createNewMenu(MenuDetailsDto menuDetailsDto);

    MenuDetailsDto getMenuById(Long menuId);

    MenuDetailsDto updateMenu(MenuDetailsDto menuDetailsDto);

    void deleteMenu(Long menuId);
}
