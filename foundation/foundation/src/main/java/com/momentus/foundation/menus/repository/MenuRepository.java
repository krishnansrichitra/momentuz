package com.momentus.foundation.menus.repository;

import com.momentus.foundation.menus.model.MenuSet;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuSet, String> {

  List<MenuSet> findByProfileProfileCodeIn(Collection<String> profileCodes);
}
