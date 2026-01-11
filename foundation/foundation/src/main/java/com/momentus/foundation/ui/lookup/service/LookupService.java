package com.momentus.foundation.ui.lookup.service;

import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.entity.service.EntityService;
import com.momentus.foundation.finitevalue.model.FiniteValue;
import com.momentus.foundation.finitevalue.service.FiniteValueService;
import com.momentus.foundation.generic.service.GenericService;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import java.util.*;
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

  public List<String> getTypeAheadValues(
      ApplicationContext context, String entity, String field, String value) {
    try {
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
