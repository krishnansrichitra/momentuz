package com.momentus.foundation.product.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "module_access_codes")
@Data // Lombok: generates getters, setters, toString, equals, hashCode
@NoArgsConstructor // Lombok: generates a no-args constructor
@AllArgsConstructor
public class ModuleAccessCodes {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "module_code", referencedColumnName = "moduleCode")
  Module module;

  @Id String accessCode;

  @Column String description;

  public Module getModule() {
    return module;
  }

  public void setModule(Module module) {
    this.module = module;
  }

  public String getAccessCode() {
    return accessCode;
  }

  public void setAccessCode(String accessCode) {
    this.accessCode = accessCode;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
