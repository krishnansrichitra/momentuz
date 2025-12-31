package com.momentus.foundation.menus.dto;

public class MenuItemDTO {

    Long id;

    String label;

    String page ;

    String icon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public MenuItemDTO() {
    }

    public MenuItemDTO(Long id, String label, String page, String icon) {
        this.id = id;
        this.label = label;
        this.page = page;
        this.icon = icon;
    }
}
