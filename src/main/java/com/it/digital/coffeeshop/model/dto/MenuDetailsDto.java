package com.it.digital.coffeeshop.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.it.digital.coffeeshop.dao.models.MenuItem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@Builder
@JsonSerialize
@JsonDeserialize
public class MenuDetailsDto {
    private Long id;
    private String name;
    private Set<Long> menuItemIdSet;
}
