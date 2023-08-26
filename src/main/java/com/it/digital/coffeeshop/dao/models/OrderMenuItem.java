package com.it.digital.coffeeshop.dao.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@JsonSerialize
@Setter
@Getter
@Table(name = "order_menu_item")
public class OrderMenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(optional = false, cascade = {CascadeType.ALL})
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne(optional = false, cascade = {CascadeType.ALL})
    @JoinColumn(name = "menu_item_id")
    MenuItem menuItem;

    Integer itemCount;
}
