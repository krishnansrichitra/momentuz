package com.momentus.foundation.menus.repository;

import com.momentus.foundation.menus.model.MenuSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface MenuRepository extends JpaRepository<MenuSet,Long> {


    List<MenuSet> findByProfileCode(Collection<String> profileCodes);

    MenuSet findByProfileId(Long profileId);



}
