package com.momentus.projmanagement.releases.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.momentus.corefw.data.EntityProperties;
import com.momentus.foundation.accessgroup.model.User;
import com.momentus.foundation.finitevalue.model.FiniteValue;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import com.momentus.projmanagement.project.model.Project;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "sprints")
public class Sprint extends OrgBasedEntity {

  @Column
  @EntityProperties(isBK = true)
  String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "release_id", nullable = true)
  Release release;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id", nullable = true)
  Project project;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id", nullable = true)
  Team team;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_owner", referencedColumnName = "userId", nullable = true)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  User productOwner;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "scrum_master", referencedColumnName = "userId", nullable = true)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  User scrumMaster;

  @Column LocalDate startDate;

  @Column LocalDate endDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sprint_status", referencedColumnName = "fv_code")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  FiniteValue status;
}
