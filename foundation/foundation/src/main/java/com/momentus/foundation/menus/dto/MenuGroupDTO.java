package com.momentus.foundation.menus.dto;

import java.util.List;

public class MenuGroupDTO {

    String id;

    String name;

    List<MenuItemDTO> items;



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

    public MenuGroupDTO(String id, String name, List<MenuItemDTO> items) {
        this.id = id;
        this.name = name;
        this.items = items;
    }
}
