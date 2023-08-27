package com.it.digital.coffeeshop.service.impl;

import com.it.digital.coffeeshop.dao.MenuItemRepository;
import com.it.digital.coffeeshop.dao.MenuRepository;
import com.it.digital.coffeeshop.dao.models.Menu;
import com.it.digital.coffeeshop.dao.models.MenuItem;
import com.it.digital.coffeeshop.model.dto.MenuDetailsDto;
import com.it.digital.coffeeshop.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuRepository menuRepository;
    @Autowired
    MenuItemRepository menuItemRepository;

    @Override
    public void createNewMenu(MenuDetailsDto menuDetailsDto) {

        Menu menu = new Menu();
        this.setMenuDetailsDtoToMenu(menuDetailsDto, menu);
        this.menuRepository.save(menu);
    }

    @Override
    public MenuDetailsDto getMenuById(Long menuId) {
        return null;
    }

    @Override
    public MenuDetailsDto updateMenu(MenuDetailsDto menuDetailsDto) {
        return null;
    }

    @Override
    public void deleteMenu(Long menuId) {

    }

    private void setMenuDetailsDtoToMenu(MenuDetailsDto menuDetailsDto, Menu menu) {
        menu.setName(menuDetailsDto.getName());
        if (menuDetailsDto.getMenuItemIdSet() != null && menuDetailsDto.getMenuItemIdSet().size() > 0) {
            Set<MenuItem> menuItemList = this.menuItemRepository.findAllByIdIn(menuDetailsDto.getMenuItemIdSet());
            menu.setMenuItemSet(menuItemList);
        }
    }


}
