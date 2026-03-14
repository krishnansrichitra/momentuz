package com.momentus.projmanagement.common;

import com.momentus.foundation.common.Utils;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.generic.dao.GenericDAO;
import com.momentus.foundation.generic.service.GenericService;
import com.momentus.foundation.ui.lookup.service.LookupService;
import com.momentus.projmanagement.project.model.Project;
import com.momentus.projmanagement.releases.model.Sprint;
import com.momentus.projmanagement.releases.service.SprintService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class CustomLookupService extends LookupService {

  @Autowired GenericService genericService;

  @Autowired GenericDAO genericDAO;

  @Override
  public Map<Long, Object> getDropDownValues(
      ApplicationContext context, String entityType, String condition) {
    if ("Project".equalsIgnoreCase(entityType)) {
      List<Project> records =
          genericService.listRecords(new HashMap(), Project.class, context, 0, 999, true);
      return (Map<Long, Object>)
          (!CollectionUtils.isEmpty(records)
              ? (Map)
                  records.stream()
                      .collect(Collectors.toMap(Project::getId, Project::getProjectDisplayTitle))
              : new HashMap());

    } else if ("Sprint".equalsIgnoreCase(entityType)) {
      StringBuffer cond =
          new StringBuffer(
              " (e.deleted = false and e.orgId.id =" + context.getOrganization().getId() + ")");
      if (StringUtils.hasLength(condition)) {
        cond.append(" and ( "  + Utils.decodeBase64(condition) + " ) ");
      }

      List<Sprint> records = genericDAO.listByFilter("Sprint",cond.toString(), Sprint.class, 0, 999);
      return (Map<Long, Object>)
          (!CollectionUtils.isEmpty(records)
              ? (Map) records.stream().collect(Collectors.toMap(Sprint::getId, Sprint::getSprintNo))
              : new HashMap());
    }
    return super.getDropDownValues(context, entityType,null);
  }
}
