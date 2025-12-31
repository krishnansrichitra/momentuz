package com.momentus.foundation.menus.dto;

import java.util.List;

public class MenuSetDTO {

    Long id;

    List<MenuGroupDTO> groups;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MenuGroupDTO> getGroups() {
        return groups;
    }

    public void setGroups(List<MenuGroupDTO> groups) {
        this.groups = groups;
    }

    public MenuSetDTO() {

    }

    public MenuSetDTO(Long id, List<MenuGroupDTO> groups) {
        this.id = id;
        this.groups = groups;
    }
}
