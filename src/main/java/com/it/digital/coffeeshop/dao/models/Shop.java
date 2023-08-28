package com.it.digital.coffeeshop.dao.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@JsonSerialize
@Setter
@Getter
@Table(name = "shop")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long lon;
    private Long lat;
    @Column(name = "mobile_num")
    private String mobileNumber;
    private String address;

    @OneToOne( cascade = {CascadeType.ALL})
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    private Menu menu;
}
