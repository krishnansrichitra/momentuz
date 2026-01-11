package com.momentus.foundation.menus.service;

import com.momentus.foundation.menus.dto.MenuDTOHelper;
import com.momentus.foundation.menus.dto.MenuSetDTO;
import com.momentus.foundation.menus.model.MenuSet;
import com.momentus.foundation.menus.repository.MenuRepository;
import com.momentus.foundation.organization.model.OrgProfile;
import com.momentus.foundation.organization.service.OrgProfileService;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class MenuService {

  @Autowired MenuRepository menuRepository;

  @Autowired OrgProfileService orgProfileService;

  @Autowired MenuDTOHelper menuDTOHelper;

  @Cacheable("MenuSet")
  public MenuSetDTO getMenuSet(Long orgId, Locale locale) {
    OrgProfile orgProfile = orgProfileService.getProfileForGroup("GNL", orgId);
    if (orgProfile != null) {
      List<MenuSet> menuSetList =
          menuRepository.findByProfileCodeIn(
              Arrays.asList(orgProfile.getProfile().getProfileCode()));
      if (!CollectionUtils.isEmpty(menuSetList)) {
        MenuSetDTO menuSetDTO =
            menuDTOHelper.makeDTO(menuSetList.get(menuSetList.size() - 1), locale);
        return menuSetDTO;
      }
    }
    return null;
  }
}
