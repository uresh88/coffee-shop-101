package com.it.digital.coffeeshop.service;

import com.it.digital.coffeeshop.exception.NotFoundException;
import com.it.digital.coffeeshop.model.dto.OrderDetailDto;
import com.it.digital.coffeeshop.model.enums.OrderStatus;

import java.util.List;
import java.util.Map;

public interface OrderService {
    void placeOrder(Long userId, Map<Long,Integer> menuItemIdQuantityList) throws NotFoundException;

    void cancelOrder(Long userId, Long orderId) throws Exception;

    List<OrderDetailDto> viewOrderListByUserId(Long userId) throws NotFoundException;

    List<OrderDetailDto> viewAllActiveOrderList(Boolean isActive) throws NotFoundException;

    void changeOrderStatus(Long orderId, OrderStatus status) throws NotFoundException;

    OrderDetailDto getOrder(Long orderId) throws NotFoundException;
}


