package com.it.digital.coffeeshop.service.impl;

import com.it.digital.coffeeshop.dao.MenuItemRepository;
import com.it.digital.coffeeshop.dao.MenuMenuItemRepository;
import com.it.digital.coffeeshop.dao.MenuRepository;
import com.it.digital.coffeeshop.dao.models.Menu;
import com.it.digital.coffeeshop.dao.models.MenuItem;
import com.it.digital.coffeeshop.dao.models.MenuMenuItem;
import com.it.digital.coffeeshop.exception.NotFoundException;
import com.it.digital.coffeeshop.model.dto.MenuDetailsDto;
import com.it.digital.coffeeshop.service.MenuService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuRepository menuRepository;
    @Autowired
    MenuItemRepository menuItemRepository;

    @Autowired
    MenuMenuItemRepository menuMenuItemRepository;

    @Override
    public void createNewMenu(MenuDetailsDto menuDetailsDto) {

        Set<MenuItem> menuItemSet = this.menuItemRepository.findAllByIdIn(menuDetailsDto.getMenuItemIdSet());

        Menu menu = new Menu();
        this.setMenuDetailsDtoToMenu(menuDetailsDto, menu);
        Menu menu1 = this.menuRepository.save(menu);

        //save conjunction table menu_menuItem
        menuItemSet.stream().map(item -> {
            return new Pair(menu1, item);
        }).map(this::newValidMenuMenuItem).collect(Collectors.toSet());
    }

    @Override
    public MenuDetailsDto getMenuDetailsDtoById(Long menuId) throws NotFoundException {
        Menu menu = getMenuById(menuId);
        return menuToMenuDetailDto(menu);
    }

    private Menu getMenuById(Long menuId) throws NotFoundException {
        Optional<Menu> optionalMenu = this.menuRepository.findById(menuId);
        if (!optionalMenu.isPresent()) {
            throw new NotFoundException("Menu item not found");
        }
        return optionalMenu.get();
    }

    @Override
    public void updateMenu(MenuDetailsDto menuDetailsDto) throws NotFoundException {
        Menu menu = this.getMenuById(menuDetailsDto.getId());
        menu.setName(menu.getName());
        Set<Long> oldMenuIdList = menu.getMenuMenuItemSet().stream()
                .map(MenuMenuItem::getMenu)
                .map(Menu::getId).collect(Collectors.toSet());
        Set<Long> newMenuIdList = menuDetailsDto.getMenuItemIdSet();
        Set<Long> delMenuIdList = new HashSet<>(oldMenuIdList);
        Set<Long> addMenuIdList = new HashSet<>(newMenuIdList);
        delMenuIdList.removeAll(newMenuIdList);
        addMenuIdList.removeAll(oldMenuIdList);
        Menu menu1 = this.menuRepository.saveAndFlush(menu);
        //delete Menu
        List<MenuItem> removedMenu = this.menuItemRepository.findAllById(delMenuIdList);
        List<MenuMenuItem> delMenuMenuItemList = this.menuMenuItemRepository.findAllByMenuItemIn(removedMenu);
        this.menuMenuItemRepository.deleteAll(delMenuMenuItemList);
        //added MenuItems
        List<MenuItem> addedMenu = this.menuItemRepository.findAllById(addMenuIdList);
        List<MenuMenuItem> addMenuMenuItemList = this.menuMenuItemRepository.findAllByMenuItemIn(addedMenu);
        addMenuMenuItemList.stream().map(x -> new Pair(menu1, x))
                .map(this::newValidMenuMenuItem)
                .forEach(item -> {
                    this.menuMenuItemRepository.save(item);
                });
    }

    @Override
    public void deleteMenu(Long menuId) throws NotFoundException {
        Menu menu = this.getMenuById(menuId);
        this.menuRepository.delete(menu);
    }

    private void setMenuDetailsDtoToMenu(MenuDetailsDto menuDetailsDto, Menu menu) {
        menu.setName(menuDetailsDto.getName());
    }

    private MenuDetailsDto menuToMenuDetailDto(Menu menu) {
        MenuDetailsDto menuDetailsDto = MenuDetailsDto.builder().name(menu.getName()).id(menu.getId()).menuItemIdSet(menu.getMenuMenuItemSet().stream().map(MenuMenuItem::getId).collect(Collectors.toSet())).build();
        return menuDetailsDto;
    }

    private MenuMenuItem newValidMenuMenuItem(Pair<Menu, MenuItem> menuItemMenuMap) {
        MenuMenuItem menuMenuItem = new MenuMenuItem();
        menuMenuItem.setMenu(menuItemMenuMap.getKey());
        menuMenuItem.setMenuItem(menuItemMenuMap.getValue());
        menuMenuItem.setIsValid(true);
        MenuMenuItem menuMenuItem1 = this.menuMenuItemRepository.save(menuMenuItem);
        return menuMenuItem1;
    }
}
