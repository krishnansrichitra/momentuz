package com.momentus.foundation.menus.dto;

public class MenuItemDTO {

    String id;

    String label;

    String page ;

    String icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public MenuItemDTO(String id, String label, String page, String icon) {
        this.id = id;
        this.label = label;
        this.page = page;
        this.icon = icon;
    }
}
