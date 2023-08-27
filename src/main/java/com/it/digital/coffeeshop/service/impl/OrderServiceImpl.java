package com.it.digital.coffeeshop.service.impl;

import com.it.digital.coffeeshop.dao.MenuItemRepository;
import com.it.digital.coffeeshop.dao.OrderMenuItemRepository;
import com.it.digital.coffeeshop.dao.OrderRepository;
import com.it.digital.coffeeshop.dao.models.MenuItem;
import com.it.digital.coffeeshop.dao.models.Order;
import com.it.digital.coffeeshop.dao.models.OrderMenuItem;
import com.it.digital.coffeeshop.dao.models.User;
import com.it.digital.coffeeshop.exception.NotFoundException;
import com.it.digital.coffeeshop.model.dto.OrderDetailDto;
import com.it.digital.coffeeshop.model.enums.OrderStatus;
import com.it.digital.coffeeshop.service.OrderService;
import com.it.digital.coffeeshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MenuItemRepository menuItemRepository;

    @Autowired
    OrderMenuItemRepository orderMenuItemRepository;

    @Autowired
    UserService userService;

    @Override
    public void placeOrder(Long userId, Map<Long, Integer> menuItemIdQuantityMap) throws NotFoundException {
        Set<MenuItem> menuItemList = this.menuItemRepository.findAllByIdIn(menuItemIdQuantityMap.keySet());
        Order order = this.saveOrderAndUpdateUser(userId, menuItemList, menuItemIdQuantityMap);
        saveOrderMenuItem(order, menuItemList, menuItemIdQuantityMap);
    }

    private Order saveOrderAndUpdateUser(Long userId, Set<MenuItem> menuItemList, Map<Long, Integer> menuItemIdQuantityMap) throws NotFoundException {
        Order order = new Order();

        Integer totalPoints = menuItemList.stream().map(item -> item.getPoint() * menuItemIdQuantityMap.get(item.getId())).reduce(0, Integer::sum);
        Double totalCost = menuItemList.stream().map(item -> item.getCost() * menuItemIdQuantityMap.get(item.getId())).reduce(0.0, Double::sum);

        order.setTotalCost(totalCost);
        order.setTotalPoint(totalPoints);
        order.setIsActive(true);
        order.setQueueNumber(11);//toDo : set number
        order.setStatus(OrderStatus.WAITING);

        User user = userService.getUserById(userId);
        user.setTotalPoints(user.getTotalPoints() + totalPoints);
        userService.updateUser(user);

        order.setUser(user);

        Order order1 = orderRepository.save(order);
        return order1;
    }

    private void saveOrderMenuItem(Order order, Set<MenuItem> menuItemList, Map<Long, Integer> menuItemIdQuantityMap) throws NotFoundException {
        for (Long menuItemId : menuItemIdQuantityMap.keySet()) {
            OrderMenuItem orderMenuItem = new OrderMenuItem();
            orderMenuItem.setItemCount(menuItemIdQuantityMap.get(menuItemId));
            orderMenuItem.setOrder(order);
            Optional<MenuItem> optMenuItem = menuItemList.stream().filter((item) -> Objects.equals(item.getId(), menuItemId)).findFirst();
            if (!optMenuItem.isPresent()) {
                throw new NotFoundException("Menu Item missing Error");
            }
            MenuItem menuItem = optMenuItem.get();
            orderMenuItem.setMenuItem(menuItem);
            this.orderMenuItemRepository.save(orderMenuItem);
        }
    }

    @Override
    public void cancelOrder(Long userId, Long orderId) throws Exception {
        Optional<Order> optOrder = this.orderRepository.findById(orderId);
        if (!optOrder.isPresent()) {
            throw new NotFoundException("Order not found");
        }

        Order order = optOrder.get();

        if (!Objects.equals(order.getUser().getId(), userId)) {
            throw new NotFoundException("Unauthorised user");
        }
        if(!order.getIsActive() || !order.getStatus().equals(OrderStatus.WAITING)){
            throw new NotFoundException("Order not in the queue");
        }
        order.setIsActive(false);
        this.orderRepository.saveAndFlush(order);
    }

    @Override
    public List<OrderDetailDto> viewOrderListByUserId(Long userId) throws NotFoundException {
        List<Order> orderList = this.orderRepository.findAllByUserIdAndIsActive(userId, true);
        if (orderList == null) {
            throw new NotFoundException("No Orders for user");
        }
        List<OrderDetailDto> orderDetailDtoList = orderList.stream().map(this::setOrderToOrderDetailDto).collect(Collectors.toList());
        return orderDetailDtoList;
    }

    @Override
    public List<OrderDetailDto> viewAllActiveOrderList(Boolean isActive) throws NotFoundException {
        List<Order> orderList = this.orderRepository.findAllByIsActive(isActive);
        if (orderList == null) {
            throw new NotFoundException("No Active orders found");
        }
        List<OrderDetailDto> orderDetailDtoList = orderList.stream().map(this::setOrderToOrderDetailDto).collect(Collectors.toList());
        return orderDetailDtoList;
    }

    /*
     * suggestion: if order is in done status don't change the status
     * */
    @Override
    public void changeOrderStatus(Long orderId, OrderStatus status) throws NotFoundException {
        Optional<Order> optOrder = this.orderRepository.findById(orderId);
        if (optOrder.isPresent()) {
            Order order = optOrder.get();
            order.setStatus(status);
            order.setIsActive(status != OrderStatus.DONE);
            this.orderRepository.saveAndFlush(order);
        }
        throw new NotFoundException("Order not Available");
    }

    @Override
    public OrderDetailDto getOrder(Long orderId) throws NotFoundException {
        Optional<Order> optOrder = this.orderRepository.findById(orderId);
        if (optOrder.isPresent()) {
            Order order = optOrder.get();
            OrderDetailDto orderDetailDto = this.setOrderToOrderDetailDto(order);
            return orderDetailDto;
        }
        throw new NotFoundException("Order not Available");
    }

    private OrderDetailDto setOrderToOrderDetailDto(Order order) {
        if (order == null) {
            return null;
        }
        OrderDetailDto orderDetailDto = OrderDetailDto.builder()
                .id(order.getId())
                .orderName(order.getOrderName())
                .isActive(order.getIsActive())
                .remarks(order.getRemarks())
                .orderName(order.getOrderName())
                .status(order.getStatus())
                .totalCost(order.getTotalCost())
                .queueNumber(order.getQueueNumber())
                .build();
        return orderDetailDto;
    }
}


