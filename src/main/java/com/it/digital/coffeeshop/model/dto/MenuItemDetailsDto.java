package com.it.digital.coffeeshop.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonSerialize
@JsonDeserialize
public class MenuItemDetailsDto {
    private Long id;
    private String remarks;
    private String name;
    private Double cost;
    private Integer point;
}
