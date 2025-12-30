package com.momentus.foundation.menus.model;

import com.momentus.foundation.profile.model.ProfileBasedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "menu_set")
public class MenuSet extends ProfileBasedEntity {

    @Column(name = "description")
    String menuSetdescription;

    public String getMenuSetdescription() {
        return menuSetdescription;
    }

    public void setMenuSetdescription(String menuSetdescription) {
        this.menuSetdescription = menuSetdescription;
    }

    @OneToMany(mappedBy = "menuSet")
    List<MenuGroup> menuGroupList;


    public List<MenuGroup> getMenuGroupList() {
        return menuGroupList;
    }

    public void setMenuGroupList(List<MenuGroup> menuGroupList) {
        this.menuGroupList = menuGroupList;
    }
}
