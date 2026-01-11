package com.momentus.foundation.menus.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "menu_group")
public class MenuGroup {

  @Id String id;

  @Column(name = "menu_key")
  String menuKey;

  @ManyToOne
  @JoinColumn(name = "menu_set_id", referencedColumnName = "id")
  @JsonIgnore
  MenuSet menuSet;

  @Column(name = "access_code")
  String accessCode;

  @Column(name = "seq_no")
  BigDecimal seqNo;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMenuKey() {
    return menuKey;
  }

  public void setMenuKey(String menuKey) {
    this.menuKey = menuKey;
  }

  public MenuSet getMenuSet() {
    return menuSet;
  }

  public void setMenuSet(MenuSet menuSet) {
    this.menuSet = menuSet;
  }

  @OneToMany(mappedBy = "menuGroup")
  @OrderBy("seqNo Asc")
  List<MenuItem> menuItemList;

  public List<MenuItem> getMenuItemList() {
    return menuItemList;
  }

  public void setMenuItemList(List<MenuItem> menuItemList) {
    this.menuItemList = menuItemList;
  }

  public String getAccessCode() {
    return accessCode;
  }

  public void setAccessCode(String accessCode) {
    this.accessCode = accessCode;
  }

  public BigDecimal getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(BigDecimal seqNo) {
    this.seqNo = seqNo;
  }
}
