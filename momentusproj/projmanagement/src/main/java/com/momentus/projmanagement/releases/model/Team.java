package com.momentus.projmanagement.releases.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.momentus.corefw.data.EntityProperties;
import com.momentus.foundation.accessgroup.model.User;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import com.momentus.projmanagement.project.model.Project;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "teams")
public class Team extends OrgBasedEntity {

  @Column
  @EntityProperties(isBK = true)
  String teamName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "release_id", nullable = true)
  Release release;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id", nullable = true)
  Project project;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_owner", referencedColumnName = "userId", nullable = true)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  User productOwner;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "scrum_master", referencedColumnName = "userId", nullable = true)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  User scrumMaster;

  @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("id Asc")
  List<TeamMember> teamMembers;

  public String getTeamName() {
    return teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  public Release getRelease() {
    return release;
  }

  public void setRelease(Release release) {
    this.release = release;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public User getProductOwner() {
    return productOwner;
  }

  public void setProductOwner(User productOwner) {
    this.productOwner = productOwner;
  }

  public User getScrumMaster() {
    return scrumMaster;
  }

  public void setScrumMaster(User scrumMaster) {
    this.scrumMaster = scrumMaster;
  }

  public List<TeamMember> getTeamMembers() {
    return teamMembers;
  }

  public void setTeamMembers(List<TeamMember> teamMembers) {
    this.teamMembers = teamMembers;
  }
}
