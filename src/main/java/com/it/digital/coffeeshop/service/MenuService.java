package com.it.digital.coffeeshop.service;

import com.it.digital.coffeeshop.dao.models.Menu;
import com.it.digital.coffeeshop.exception.NotFoundException;
import com.it.digital.coffeeshop.model.dto.MenuDetailsDto;

public interface MenuService {
    void createNewMenu(MenuDetailsDto menuDetailsDto);

    MenuDetailsDto getMenuDetailsDtoById(Long menuId) throws NotFoundException;

    void updateMenu(MenuDetailsDto menuDetailsDto) throws NotFoundException;

    void deleteMenu(Long menuId) throws NotFoundException;

    Menu getMenuById(Long menuId) throws NotFoundException;
}
