package com.it.digital.coffeeshop.dao.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@JsonSerialize
@Setter
@Getter
@Table(name = "menu_item")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String remarks;

    private String name;

    private Double cost;

    private Integer point;

    @OneToMany(mappedBy = "menuItem", cascade = {CascadeType.ALL})
    private Set<OrderMenuItem> orderMenuItemSet;


}
