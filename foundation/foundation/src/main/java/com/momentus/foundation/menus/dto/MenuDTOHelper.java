package com.momentus.foundation.menus.dto;

import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.menus.model.MenuGroup;
import com.momentus.foundation.menus.model.MenuItem;
import com.momentus.foundation.menus.model.MenuSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class MenuDTOHelper {

    @Autowired
    GeneralMessages generalMessages;

    public MenuSetDTO makeDTO(MenuSet menuSet, Locale ls)
    {
        MenuSetDTO menuSetDTO =null;
        if (menuSet != null && !CollectionUtils.isEmpty(menuSet.getMenuGroupList()) ) {
            List<MenuGroupDTO> menuGroupDTOList = new ArrayList<>();
            for (MenuGroup menuGroup : menuSet.getMenuGroupList()) {
                List<MenuItemDTO> menuItemDTOList = new ArrayList<>();
                if (menuGroup !=null && !CollectionUtils.isEmpty(menuGroup.getMenuItemList())) {
                    for (MenuItem menuItem : menuGroup.getMenuItemList()) {
                        MenuItemDTO menuItemDTO = new MenuItemDTO(menuItem.getId(), generalMessages.getMessage(menuItem.getMenuKey(), ls), menuItem.getPage(), null);
                        menuItemDTOList.add(menuItemDTO);
                    }
                }
                MenuGroupDTO menuGroupDTO = new MenuGroupDTO(menuGroup.getId(),generalMessages.getMessage(menuGroup.getMenuKey(),ls),menuItemDTOList);
                menuGroupDTOList.add(menuGroupDTO);
            }
            menuSetDTO = new MenuSetDTO(menuSet.getId(),menuGroupDTOList);
        }
        return  menuSetDTO;
    }
}
