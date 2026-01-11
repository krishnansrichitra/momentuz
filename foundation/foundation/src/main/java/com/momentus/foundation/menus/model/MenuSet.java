package com.momentus.foundation.menus.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.momentus.corefw.data.EntityProperties;
import com.momentus.foundation.profile.model.ProfileBasedEntity;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "menu_set")
public class MenuSet extends ProfileBasedEntity {

  @Id
  @EntityProperties(isPK = true)
  String id;

  @Column(name = "description")
  String menuSetdescription;

  public String getMenuSetdescription() {
    return menuSetdescription;
  }

  public void setMenuSetdescription(String menuSetdescription) {
    this.menuSetdescription = menuSetdescription;
  }

  @OneToMany(mappedBy = "menuSet")
  @OrderBy("seqNo Asc")
  List<MenuGroup> menuGroupList;

  public List<MenuGroup> getMenuGroupList() {
    return menuGroupList;
  }

  public void setMenuGroupList(List<MenuGroup> menuGroupList) {
    this.menuGroupList = menuGroupList;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  @JsonIgnore
  public Object getPK() {
    return id;
  }
}
