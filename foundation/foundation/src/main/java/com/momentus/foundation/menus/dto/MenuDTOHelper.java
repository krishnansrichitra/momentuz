package com.momentus.foundation.menus.dto;

import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.common.Utils;
import com.momentus.foundation.menus.model.MenuGroup;
import com.momentus.foundation.menus.model.MenuItem;
import com.momentus.foundation.menus.model.MenuSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class MenuDTOHelper {

  @Autowired GeneralMessages generalMessages;

  public MenuSetDTO makeDTO(MenuSet menuSet, Locale ls, Set<String> accessCodes) {
    MenuSetDTO menuSetDTO = null;
    if (menuSet != null && !CollectionUtils.isEmpty(menuSet.getMenuGroupList())) {
      List<MenuGroupDTO> menuGroupDTOList = new ArrayList<>();
      for (MenuGroup menuGroup : menuSet.getMenuGroupList()) {
        Boolean hasAllAccess = !StringUtils.hasLength(menuGroup.getAccessCode());
        Set<String> permissibleCodes = Utils.splitCSV(menuGroup.getAccessCode());
        if (hasAllAccess || Utils.hasCommonEntry(accessCodes, permissibleCodes)) {
          List<MenuItemDTO> menuItemDTOList = new ArrayList<>();
          if (menuGroup != null && !CollectionUtils.isEmpty(menuGroup.getMenuItemList())) {
            for (MenuItem menuItem : menuGroup.getMenuItemList()) {
              if (accessCodes.contains(menuItem.getAccessCode())) {
                MenuItemDTO menuItemDTO =
                    new MenuItemDTO(
                        menuItem.getId(),
                        generalMessages.getMessage(menuItem.getMenuKey(), ls),
                        menuItem.getPage(),
                        null,
                        menuItem.getHasChildren(),
                        menuItem.getParentItem());
                menuItemDTOList.add(menuItemDTO);
              }
            }
          }
          MenuGroupDTO menuGroupDTO =
              new MenuGroupDTO(
                  menuGroup.getId(),
                  generalMessages.getMessage(menuGroup.getMenuKey(), ls),
                  menuItemDTOList);
          menuGroupDTOList.add(menuGroupDTO);
        }
      }
      menuSetDTO = new MenuSetDTO(menuSet.getId(), menuGroupDTOList);
    }
    return menuSetDTO;
  }
}
