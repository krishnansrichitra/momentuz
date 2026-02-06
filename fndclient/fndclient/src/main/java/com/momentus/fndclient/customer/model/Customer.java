package com.momentus.fndclient.customer.model;

import com.momentus.corefw.data.EntityProperties;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "customers")
public class Customer extends OrgBasedEntity {

  @Column
  @EntityProperties(isBK = true)
  String name;

  @Column Long age;

  @Column String address1;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getAge() {
    return age;
  }

  public void setAge(Long age) {
    this.age = age;
  }

  public String getAddress1() {
    return address1;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  @Override
  public Map<String, Object> getBK() {

    Map<String, Object> mp = new HashMap<>();
    mp.put("name", name);
    return mp;
  }
}
