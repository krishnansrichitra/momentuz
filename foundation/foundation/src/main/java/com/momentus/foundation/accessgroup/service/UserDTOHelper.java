package com.momentus.foundation.accessgroup.service;

import com.momentus.foundation.accessgroup.dto.RoleDTO;
import com.momentus.foundation.accessgroup.dto.UserDTO;
import com.momentus.foundation.accessgroup.dto.UserRoleDTO;
import com.momentus.foundation.accessgroup.model.Role;
import com.momentus.foundation.accessgroup.model.User;
import com.momentus.foundation.accessgroup.model.UserRoles;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.generic.service.GenericService;
import com.momentus.foundation.generic.service.MapToEntityMapper;
import com.momentus.foundation.organization.dto.DivisionDTO;
import com.momentus.foundation.organization.model.Division;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class UserDTOHelper {

  @Autowired GenericService genericService;

  @Autowired MapToEntityMapper mapToEntityMapper;

  public User makeUserFromDTO(UserDTO userDTO, ApplicationContext context) {
    User user = new User();
    user.setUserId(userDTO.getUserId());
    user.setEmail(userDTO.getEmail());
    user.setFirstName(userDTO.getFirstName());
    user.setLastName(userDTO.getLastName());
    user.setPhone(user.getPhone());
    user.setOrgId(context.getOrganization());
    if (userDTO.getDivision() != null) {
      DivisionDTO divisionDTO = userDTO.getDivision();
      Division division = new Division();
      division.setId(divisionDTO.getId());
      division.setTitle(divisionDTO.getTitle());
      division = (Division) mapToEntityMapper.loadObjectFromDB(division, context);
      user.setDivision(division);
    }
    if (!CollectionUtils.isEmpty(userDTO.getUserRoles())) {
      List<UserRoles> userRolesList = new ArrayList<>();
      for (UserRoleDTO userRoleDTO : userDTO.getUserRoles()) {
        UserRoles userRoles = new UserRoles();
        userRoles.setUser(user);
        RoleDTO roleDTO = userRoleDTO.getRole();
        Role role = new Role();
        role.setTitle(role.getTitle());
        role.setId(roleDTO.getId());
        role = (Role) mapToEntityMapper.loadObjectFromDB(role, context);
        userRoles.setRole(role);
        userRolesList.add(userRoles);
      }
      user.setUserRoles(userRolesList);
    }
    return user;
  }

  public UserDTO makeDTOFromUser(User user, ApplicationContext context) {
    DivisionDTO divisionDTO = null;
    if (user.getDivision() != null)
      divisionDTO =
          new DivisionDTO(
              user.getDivision().getId(),
              user.getDivision().getTitle(),
              user.getDivision().getDivisionCode());

    List<UserRoleDTO> userRolesList = new ArrayList<>();
    if (!CollectionUtils.isEmpty(user.getUserRoles())) {
      for (UserRoles userRoles : user.getUserRoles()) {
        UserRoleDTO userRoleDTO =
            new UserRoleDTO(
                userRoles.getId(),
                user.getUserId(),
                new RoleDTO(userRoles.getRole().getId(), userRoles.getRole().getTitle()),
                userRoles.getVersion());
        userRolesList.add(userRoleDTO);
      }
    }

    UserDTO userDTO =
        new UserDTO(
            user.getUserId(),
            user.getFirstName(),
            user.getLastName(),
            user.getPhone(),
            user.getEmail(),
            divisionDTO,
            userRolesList,
            user.getVersion());
    return userDTO;
  }
}
