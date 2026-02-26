package com.momentus.foundation.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.momentus.corefw.data.EntityProperties;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MappedSuperclass
public abstract class BaseEntity {

  private static final Logger log = LoggerFactory.getLogger(BaseEntity.class);

  @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
  private boolean deleted = false;

  @Column(name = "created_by", nullable = true)
  private String createdBy;

  @Column(name = "created_time", nullable = true)
  private LocalDateTime createdTime;

  @Column(name = "last_updated_by", nullable = true)
  private String lastUpdatedBy;

  @Column(name = "last_updated_time", nullable = true)
  private LocalDateTime lastUpdatedTime;

  @Version private Long version;

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public LocalDateTime getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(LocalDateTime createdTime) {
    this.createdTime = createdTime;
  }

  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  public LocalDateTime getLastUpdatedTime() {
    return lastUpdatedTime;
  }

  public void setLastUpdatedTime(LocalDateTime lastUpdatedTime) {
    this.lastUpdatedTime = lastUpdatedTime;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  @JsonIgnore
  public Object getPK() {
    return null;
  }

  @JsonIgnore
  public Map<String, Object> getBK() {
    Map<String, Object> result = new HashMap<>();
    Class<?> clazz = this.getClass();
    for (Field field : clazz.getDeclaredFields()) {
      EntityProperties annotation = field.getAnnotation(EntityProperties.class);
      if (annotation != null && annotation.isBK()) {
        field.setAccessible(true);
        try {
          Object value = field.get(this);
          result.put(field.getName(), value);
        } catch (Exception ex) {
          log.error("error in  getting BK", ex);
        }
      }
    }

    return result;
  }

  @JsonIgnore
  public void setBK(Object object) {
    Class<?> clazz = this.getClass();
    for (Field field : clazz.getDeclaredFields()) {
      EntityProperties annotation = field.getAnnotation(EntityProperties.class);
      if (annotation != null && annotation.isBK()) {
        field.setAccessible(true);
        try {
          field.set(this, object);
        } catch (Exception ex) {
          log.error("error in  setting BK", ex);
        }
      }
    }
  }

  @JsonIgnore
  public String getBKField() {
    Class<?> clazz = this.getClass();
    for (Field field : clazz.getDeclaredFields()) {
      EntityProperties annotation = field.getAnnotation(EntityProperties.class);
      if (annotation != null && annotation.isBK()) {
        return field.getName();
      }
    }
    return null;
  }

  @JsonIgnore
  public Object getBKValue() {
    try {
      Class<?> clazz = this.getClass();
      for (Field field : clazz.getDeclaredFields()) {
        EntityProperties annotation = field.getAnnotation(EntityProperties.class);
        if (annotation != null && annotation.isBK()) {
          field.setAccessible(true);
          return field.get(this);
        }
      }
    } catch (Exception ex) {
      return null;
    }
    return null;
  }

  @JsonIgnore
  public Map<String, Object> getMandatoryFields() {
    Map<String, Object> result = new HashMap<>();
    Class<?> clazz = this.getClass();
    for (Field field : clazz.getDeclaredFields()) {
      EntityProperties annotation = field.getAnnotation(EntityProperties.class);
      if (annotation != null && annotation.isMandatory()) {
        field.setAccessible(true);
        try {
          Object value = field.get(this);
          result.put(field.getName(), value);
        } catch (Exception ex) {
          log.error("error in  getting BK", ex);
        }
      }
    }
    return result;
  }

  public Map<String, Object> geUniqueFields() {
    Map<String, Object> result = new HashMap<>();
    Class<?> clazz = this.getClass();
    for (Field field : clazz.getDeclaredFields()) {
      EntityProperties annotation = field.getAnnotation(EntityProperties.class);
      if (annotation != null && annotation.isUnique()) {
        field.setAccessible(true);
        try {
          Object value = field.get(this);
          result.put(field.getName(), value);
        } catch (Exception ex) {
          log.error("error in  getting BK", ex);
        }
      }
    }
    return result;
  }

  @JsonIgnore
  public void setUniqueFieldsFromMap(Map<String, Object> mp) {
    Class<?> clazz = this.getClass();
    for (Field field : clazz.getDeclaredFields()) {
      EntityProperties annotation = field.getAnnotation(EntityProperties.class);
      if (annotation != null && annotation.isUnique()) {
        field.setAccessible(true);
        try {
          field.set(this, mp.get(field.getName()));
        } catch (Exception ex) {
          log.error("error in  setting BK", ex);
        }
      }
    }
  }

  public void setParentObject(BaseEntity base) {
    // do nothing
  }
}
