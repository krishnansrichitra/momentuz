package com.momentus.projmanagement.releases.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.momentus.foundation.accessgroup.model.User;
import com.momentus.foundation.common.model.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "team_members")
public class TeamMember extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner", referencedColumnName = "userId", nullable = true)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  User member;

  @Column Float capacityPerSprint;

  @Column String description;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "team_id", nullable = true)
  @JsonIgnore
  Team team;

  public User getMember() {
    return member;
  }

  public void setMember(User member) {
    this.member = member;
  }

  public Float getCapacityPerSprint() {
    return capacityPerSprint;
  }

  public void setCapacityPerSprint(Float capacityPerSprint) {
    this.capacityPerSprint = capacityPerSprint;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Team getTeam() {
    return team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
