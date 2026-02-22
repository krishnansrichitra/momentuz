package com.momentus.foundation.menus.service;

import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.menus.dto.MenuDTOHelper;
import com.momentus.foundation.menus.dto.MenuSetDTO;
import com.momentus.foundation.menus.model.MenuSet;
import com.momentus.foundation.menus.repository.MenuRepository;
import com.momentus.foundation.organization.model.OrgProfile;
import com.momentus.foundation.organization.service.OrgProfileService;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class MenuService {

  @Autowired MenuRepository menuRepository;

  @Autowired OrgProfileService orgProfileService;

  @Autowired MenuDTOHelper menuDTOHelper;

  private List<String> getProfileCodes(String fullProfile) {
    if (fullProfile == null || fullProfile.isBlank()) return Collections.emptyList();

    String[] parts = fullProfile.split("-");
    List<String> result = new ArrayList<>(Arrays.asList(parts));

    Collections.reverse(result);
    return result;
  }

  @Cacheable("MenuSetList")
  public List<MenuSet> getMenuSetList(Long orgId) {
    OrgProfile orgProfile = orgProfileService.getProfileForGroup("GNL", orgId);
    if (orgProfile != null) {
      return menuRepository.findByProfileProfileCodeIn(
          getProfileCodes(orgProfile.getProfile().getFullProfileCode()));
    } else return null;
  }

  public MenuSetDTO getMenuSet(List<MenuSet> menuSetList, ApplicationContext context) {
    if (!CollectionUtils.isEmpty(menuSetList)) {
      MenuSetDTO menuSetDTO =
          menuDTOHelper.makeDTO(menuSetList.get(menuSetList.size() - 1), context.getLocale());

      return menuSetDTO;
    }
    return null;
  }
}
