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
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "menu", cascade = {CascadeType.ALL})
    private Set<MenuMenuItem> menuMenuItemSet;
}

