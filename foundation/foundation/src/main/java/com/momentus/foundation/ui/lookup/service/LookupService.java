package com.momentus.foundation.ui.lookup.service;

import com.momentus.foundation.accessgroup.model.Role;
import com.momentus.foundation.accessgroup.model.User;
import com.momentus.foundation.accessgroup.repository.UserRepository;
import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.entity.service.EntityService;
import com.momentus.foundation.finitevalue.model.FiniteValue;
import com.momentus.foundation.finitevalue.service.FiniteValueService;
import com.momentus.foundation.generic.service.GenericService;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import com.momentus.foundation.product.model.ModuleAccessCodes;
import com.momentus.foundation.product.repository.ModuleAccessCodeRepository;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class LookupService {

  private static final Logger log = LoggerFactory.getLogger(LookupService.class);

  @Autowired FiniteValueService finiteValueService;

  @Autowired GenericService genericService;

  @Autowired GeneralMessages generalMessages;

  @Autowired EntityService entityService;

  @Autowired ModuleAccessCodeRepository moduleAccessCodeRepository;

  @Autowired UserRepository userRepository;

  @Cacheable("finiteValues")
  public Map<String, String> getFValuesforDropDown(String groupCode, Locale locale) {
    Map<String, String> retValues = new LinkedHashMap<>();
    List<FiniteValue> finiteValueList = finiteValueService.getFiniteValueByGroup(groupCode);
    if (!CollectionUtils.isEmpty(finiteValueList)) {
      for (FiniteValue finiteValue : finiteValueList) {
        retValues.put(
            finiteValue.getFvCode(), generalMessages.getMessage(finiteValue.getFvValue(), locale));
        // retValues.put(finiteValue.getFvCode(),finiteValue.getFvValue());
      }
    }
    return retValues;
  }

  public Map<String, String> getAccessCodes() {

    List<ModuleAccessCodes> records = moduleAccessCodeRepository.findAll();
    if (!CollectionUtils.isEmpty(records)) {
      return records.stream()
          .collect(
              Collectors.toMap(
                  ModuleAccessCodes::getAccessCode, ModuleAccessCodes::getDescription));
    } else {
      return new HashMap<>();
    }
  }

  public Map<Long, String> getRoles(ApplicationContext context) {
    List<Role> records =
        (List<Role>)
            genericService.listRecords(
                new HashMap<String, Object>(),
                (Class<? extends OrgBasedEntity>) Role.class,
                context,
                0,
                999,
                true);
    if (!CollectionUtils.isEmpty(records)) {
      return records.stream().collect(Collectors.toMap(Role::getId, Role::getTitle));
    } else {
      return new HashMap<>();
    }
  }

  public Map<Long, Object> getDropDownValues(ApplicationContext context, String entityType) {

    String fullPackage = entityService.getFullPackage(entityType);
    try {
      Class<OrgBasedEntity> entityClass = (Class<OrgBasedEntity>) Class.forName(fullPackage);
      List<OrgBasedEntity> records =
          (List<OrgBasedEntity>)
              genericService.listRecords(
                  new HashMap<String, Object>(),
                  (Class<? extends OrgBasedEntity>) entityClass,
                  context,
                  0,
                  999,
                  true);
      if (!CollectionUtils.isEmpty(records)) {
        return records.stream()
            .collect(Collectors.toMap(OrgBasedEntity::getId, OrgBasedEntity::getBKValue));
      } else {
        return new HashMap<>();
      }
    } catch (Exception e) {
      log.error("Error while looking up  " + entityType, e);
      throw new RuntimeException(e);
    }
  }

  public List<String> getTypeAheadUsers(ApplicationContext context, String value) {
    List<String> retValues = new ArrayList<>();
    List<User> users = userRepository.searchUsers(value, context.getOrganization().getId());
    if (!CollectionUtils.isEmpty(users)) {
      for (User user : users) {
        retValues.add(user.getUserId());
      }
    }
    return retValues;
  }

  public List<String> getTypeAheadValues(
      ApplicationContext context, String entity, String field, String value) {
    try {

      if ("User".equalsIgnoreCase(entity)) {
        return getTypeAheadUsers(context, value);
      }
      Map<String, Object> filter = new HashMap<>();
      filter.put(field, value);
      String entityClass = entityService.getFullPackage(entity);
      List<String> records =
          genericService.listFields(
              filter,
              (Class<? extends OrgBasedEntity>) Class.forName(entityClass),
              context,
              field,
              0,
              999,
              true);
      return records;
    } catch (Exception ex) {
      log.error("Error while looking up data ", ex);
      return Arrays.asList();
    }
  }
}
