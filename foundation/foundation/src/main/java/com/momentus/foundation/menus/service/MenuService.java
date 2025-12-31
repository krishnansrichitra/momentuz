package com.momentus.foundation.menus.service;


import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.menus.model.MenuSet;
import com.momentus.foundation.menus.repository.MenuRepository;
import com.momentus.foundation.organization.model.OrgProfile;
import com.momentus.foundation.organization.service.OrgProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    OrgProfileService orgProfileService;

    public MenuSet getMenuSet(ApplicationContext context){
       OrgProfile orgProfile =  orgProfileService.getProfileForGroup("GNL",context.getOrganization().getId());
       if (orgProfile != null) {
        List<MenuSet> menuSetList=   menuRepository.findByProfileCodeIn(Arrays.asList(orgProfile.getProfile().getProfileCode()));
        if (!CollectionUtils.isEmpty(menuSetList)){
            return menuSetList.get(menuSetList.size()-1);
        }
       }
       return null;
    }
}
