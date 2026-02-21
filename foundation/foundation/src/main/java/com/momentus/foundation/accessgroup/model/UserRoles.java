package com.momentus.foundation.accessgroup.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.momentus.foundation.common.model.BaseEntity;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRoles extends OrgBasedEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "userId")
  @JsonIgnore
  private User user;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "role_id", referencedColumnName = "Id")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

  @Override
  public void setParentObject(BaseEntity base) {
    setUser((User) base);
  }
}
