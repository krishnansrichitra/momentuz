package com.momentus.foundation.menus.model;

import com.momentus.corefw.data.EntityProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "menu_group")
public class MenuGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "menu_key")
    String menuKey;

    @ManyToOne
    @JoinColumn(name = "menu_set_id" , referencedColumnName = "id")
    MenuSet menuSet;


    @Column(name = "access_code")
    String accessCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
