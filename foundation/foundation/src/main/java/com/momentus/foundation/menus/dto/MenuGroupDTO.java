package com.momentus.foundation.menus.dto;

import java.util.List;

public class MenuGroupDTO {

    Long id;

    String name;

    List<MenuItemDTO> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MenuItemDTO> getItems() {
        return items;
    }

    public void setItems(List<MenuItemDTO> items) {
        this.items = items;
    }

    public MenuGroupDTO() {
    }

    public MenuGroupDTO(Long id, String name, List<MenuItemDTO> items) {
        this.id = id;
        this.name = name;
        this.items = items;
    }
}
