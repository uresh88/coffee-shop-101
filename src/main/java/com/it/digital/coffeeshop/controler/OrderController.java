package com.it.digital.coffeeshop.controler;

import com.it.digital.coffeeshop.model.dto.OrderDetailDto;
import com.it.digital.coffeeshop.model.enums.OrderStatus;
import com.it.digital.coffeeshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(path = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PutMapping(value = "/add/{userId}")
    public ResponseEntity placeOrder(@RequestBody Map<Long, Integer> menuItemIdQuantityList, @PathVariable Long userId) throws Exception {
        this.orderService.placeOrder(userId, menuItemIdQuantityList);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/cancel/{userId}/{orderId}")
    public ResponseEntity cancelOrder(@PathVariable Long userId, @PathVariable Long orderId) throws Exception {
        this.orderService.cancelOrder(userId, orderId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/list/{userId}")
    public ResponseEntity<List<OrderDetailDto>> viewOrderListByUser(@PathVariable Long userId) throws Exception {
        List<OrderDetailDto> orderDetailDtoList = this.orderService.viewOrderListByUserId(userId);
        return ResponseEntity.ok().body(orderDetailDtoList);
    }

    @PostMapping(value = "/list/{isActive}")
    public ResponseEntity<List<OrderDetailDto>> viewActiveOrder(@PathVariable boolean isActive) throws Exception {
        List<OrderDetailDto> orderDetailDtoList = this.orderService.viewAllActiveOrderList(isActive);
        return ResponseEntity.ok().body(orderDetailDtoList);
    }

    @GetMapping(value = "/{orderId}/change/{status}")
    public ResponseEntity processOrder(@PathVariable Long orderId, @PathVariable OrderStatus status) throws Exception {
        this.orderService.changeOrderStatus(orderId, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{orderId}")
    public ResponseEntity<OrderDetailDto> viewOrder(@PathVariable Long orderId) throws Exception {
        OrderDetailDto orderDetailDto = this.orderService.getOrder(orderId);
        return ResponseEntity.ok().body(orderDetailDto);
    }


}
