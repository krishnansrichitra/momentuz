package com.momentus.projmanagement.releases.dto;

public class SprintGenerationDTO {

  String prefix;

  Long releaseId;

  Long teamId;

  Integer seqStart;

  Integer seqEnd;

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public Long getReleaseId() {
    return releaseId;
  }

  public void setReleaseId(Long releaseId) {
    this.releaseId = releaseId;
  }

  public Long getTeamId() {
    return teamId;
  }

  public void setTeamId(Long teamId) {
    this.teamId = teamId;
  }

  public Integer getSeqStart() {
    return seqStart;
  }

  public void setSeqStart(Integer seqStart) {
    this.seqStart = seqStart;
  }

  public Integer getSeqEnd() {
    return seqEnd;
  }

  public void setSeqEnd(Integer seqEnd) {
    this.seqEnd = seqEnd;
  }
}
