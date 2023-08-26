package com.it.digital.coffeeshop.dao.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.it.digital.coffeeshop.model.enums.UserType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@JsonSerialize
@Setter
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_type", nullable = false)
    @NonNull
    private UserType userType;

    @Column(nullable = false)
    private String name;
    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;
    @Column(name = "regular_address", nullable = false)
    private String regularAddress;

    private Integer totalPoints;

    @OneToMany(mappedBy="user")
    private Set<Order> orders;


}
