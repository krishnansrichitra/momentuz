package com.momentus.foundation.menus.dto;

public class MenuItemDTO {

  String id;

  String label;

  String page;

  String icon;

  Boolean hasChildren;

  String parentItem;

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

  public MenuItemDTO() {}

  public Boolean getHasChildren() {
    return hasChildren;
  }

  public void setHasChildren(Boolean hasChildren) {
    this.hasChildren = hasChildren;
  }

  public String getParentItem() {
    return parentItem;
  }

  public void setParentItem(String parentItem) {
    this.parentItem = parentItem;
  }

  public MenuItemDTO(
      String id, String label, String page, String icon, Boolean hasChildren, String parentItem) {
    this.id = id;
    this.label = label;
    this.page = page;
    this.icon = icon;
    this.hasChildren = hasChildren;
    this.parentItem = parentItem;
  }
}
