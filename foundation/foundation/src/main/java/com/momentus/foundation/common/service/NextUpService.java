package com.momentus.foundation.common.service;

import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.nextup.model.NextUpConfig;
import com.momentus.foundation.common.nextup.model.NextUpData;
import com.momentus.foundation.common.repository.NextUpConfigRepository;
import com.momentus.foundation.common.repository.NextUpDataRepository;
import com.momentus.foundation.finitevalue.model.FiniteValue;
import com.momentus.foundation.organization.model.OrgProfile;
import com.momentus.foundation.organization.service.OrgProfileService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class NextUpService {

  @Autowired OrgProfileService orgProfileService;

  @Autowired NextUpConfigRepository nextUpConfigRepository;

  @Autowired NextUpDataRepository nextUpDataRepository;

  public String getNextUpNo(
      ApplicationContext context,
      String entity,
      String constant,
      String component1,
      String component2,
      String component3) {
    OrgProfile orgProfile =
        orgProfileService.getProfileForGroup("GNL", context.getOrganization().getId());
    NextUpConfig config =
        nextUpConfigRepository.findByProfile_ProfileCodeAndEntity(
            orgProfile.getProfile().getProfileCode(), entity);
    List<NextUpData> nextUpDataList =
        nextUpDataRepository.findByConfig_EntityAndOrgId_IdAndComponent1AndComponent2AndComponent3(
            entity, context.getOrganization().getId(), component1, component2, component3);
    if (CollectionUtils.isEmpty(nextUpDataList)) {

      NextUpData nextUpData1 = new NextUpData();
      nextUpData1.setOrgId(context.getOrganization());
      nextUpData1.setConfig(config);
      nextUpData1.setComponent1(component1);
      nextUpData1.setComponent2(component2);
      nextUpData1.setComponent3(component3);
      nextUpData1.setLastSeqValue(1L);
      nextUpDataRepository.save(nextUpData1);
      return getNexUpNumber(config, 1L, constant, component1, component2, component3);
    } else {
      NextUpData nextUpData = nextUpDataList.get(0);
      Long lastValue = nextUpData.getLastSeqValue();
      nextUpDataRepository.updateNextUpCounter(nextUpData.getId(), lastValue + 1);
      return getNexUpNumber(config, lastValue + 1, constant, component1, component2, component3);
    }
  }

  private String getNexUpNumber(
      NextUpConfig nextUpConfig,
      Long sequence,
      String constant,
      String component1,
      String component2,
      String component3) {
    StringBuffer retValue = new StringBuffer();
    if (nextUpConfig.getField1() != null) {
      retValue.append(
          getFieldValue(
              nextUpConfig.getField1(),
              nextUpConfig,
              sequence,
              constant,
              component1,
              component2,
              component3));
    }
    if (nextUpConfig.getField2() != null) {
      retValue.append(
          getFieldValue(
              nextUpConfig.getField2(),
              nextUpConfig,
              sequence,
              constant,
              component1,
              component2,
              component3));
    }
    if (nextUpConfig.getField3() != null) {
      retValue.append(
          getFieldValue(
              nextUpConfig.getField3(),
              nextUpConfig,
              sequence,
              constant,
              component1,
              component2,
              component3));
    }
    if (nextUpConfig.getField4() != null) {
      retValue.append(
          getFieldValue(
              nextUpConfig.getField4(),
              nextUpConfig,
              sequence,
              constant,
              component1,
              component2,
              component3));
    }
    if (nextUpConfig.getField5() != null) {
      retValue.append(
          getFieldValue(
              nextUpConfig.getField5(),
              nextUpConfig,
              sequence,
              constant,
              component1,
              component2,
              component3));
    }

    return retValue.toString();
  }

  private String getFieldValue(
      FiniteValue finiteValue,
      NextUpConfig config,
      Long sequence,
      String constant,
      String component1,
      String component2,
      String component3) {
    if ("nxtup_dt".equalsIgnoreCase(finiteValue.getFvCode())) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(config.getDateFormat());
      return LocalDate.now().format(formatter);
    }

    if ("nxtup_prfx".equalsIgnoreCase(finiteValue.getFvCode())) {
      return config.getPrefix();
    }

    if ("nxtup_seq".equalsIgnoreCase(finiteValue.getFvCode())) {
      String s = String.format("%0" + config.getSequenceWidth() + "d", sequence);
      return s;
    }

    if ("nxtup_BK".equalsIgnoreCase(finiteValue.getFvCode())) {
      return constant;
    }

    if ("nxtup_comp1".equalsIgnoreCase(finiteValue.getFvCode())) {
      return component1;
    }
    if ("nxtup_comp2".equalsIgnoreCase(finiteValue.getFvCode())) {
      return component2;
    }
    if ("nxtup_comp3".equalsIgnoreCase(finiteValue.getFvCode())) {
      return component3;
    }

    return ";";
  }
}
