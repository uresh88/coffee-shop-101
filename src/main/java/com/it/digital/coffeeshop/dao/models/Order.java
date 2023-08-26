package com.it.digital.coffeeshop.dao.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.it.digital.coffeeshop.model.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@JsonSerialize
@Setter
@Getter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "menu_item_id")
    private Long menuItemId;

    @Column(name = "order_name")
    private String orderName;

    @Column(name = "remarks")
    private String remarks;

    private OrderStatus status;

    private Boolean isActive;

    private Integer queueNumber;

    @Column(name = "total_cost")
    private Double totalCost;

    @Column(name = "total_point")
    private Integer totalPoint;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.ALL})
    private Set<OrderMenuItem> orderMenuItemSet;


    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    private User user;

}
