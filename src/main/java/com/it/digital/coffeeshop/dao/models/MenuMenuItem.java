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
@Table(name = "menu_menu_item")
public class MenuMenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isValid;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "menu_id")
    private Menu menu ;

}
