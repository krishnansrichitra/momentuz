package com.momentus.foundation.accessgroup.model;

import com.momentus.foundation.organization.model.OrgBasedEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRoles extends OrgBasedEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "userId")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "role_id", referencedColumnName = "Id")
  private Role role;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
