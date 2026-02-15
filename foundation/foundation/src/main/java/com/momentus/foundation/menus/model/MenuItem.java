package com.momentus.foundation.menus.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "menu_item")
public class MenuItem {

  @Id String id;

  @ManyToOne
  @JoinColumn(name = "menu_group_id", referencedColumnName = "id")
  @JsonIgnore
  MenuGroup menuGroup;

  @Column(name = "menu_key")
  String menuKey;

  @Column(name = "access_code")
  String accessCode;

  @Column(name = "page")
  String page;

  @Column(name = "seq_no")
  BigDecimal seqNo;

  @Column(name = "has_children")
  Boolean hasChildren;

  @Column(name = "parent_item")
  String parentItem;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public MenuGroup getMenuGroup() {
    return menuGroup;
  }

  public void setMenuGroup(MenuGroup menuGroup) {
    this.menuGroup = menuGroup;
  }

  public String getMenuKey() {
    return menuKey;
  }

  public void setMenuKey(String menuKey) {
    this.menuKey = menuKey;
  }

  public String getAccessCode() {
    return accessCode;
  }

  public void setAccessCode(String accessCode) {
    this.accessCode = accessCode;
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public BigDecimal getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(BigDecimal seqNo) {
    this.seqNo = seqNo;
  }

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
}
