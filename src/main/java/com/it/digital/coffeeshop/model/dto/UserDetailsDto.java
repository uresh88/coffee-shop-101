package com.it.digital.coffeeshop.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.it.digital.coffeeshop.model.enums.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonSerialize
@JsonDeserialize
public class UserDetailsDto {
    private Long id;
    private UserType userType;
    private String name;
    private String mobileNumber;
    private String regularAddress;
    private Integer totalPoints;
}
