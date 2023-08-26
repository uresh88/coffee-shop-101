package com.it.digital.coffeeshop.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@JsonSerialize
@JsonDeserialize
public class ShopDetailDto {
    private Long id;
    private String name;
    private Long lon;
    private Long lat;
    private String mobileNumber;
    private String address;
}
