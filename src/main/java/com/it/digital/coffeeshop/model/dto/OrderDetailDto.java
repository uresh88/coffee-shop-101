package com.it.digital.coffeeshop.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.it.digital.coffeeshop.model.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@JsonSerialize
@JsonDeserialize

public class OrderDetailDto {
    private Long id;
    private Long userId;
    private OrderStatus status;
    private Boolean isActive;
    private Integer queueNumber;
    private String orderName;
    private String remarks;
    private Double totalCost;
    private Integer totalPoint;
}


