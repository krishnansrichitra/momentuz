package com.momentus.foundation.ui.lookup.service;

import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.finitevalue.model.FiniteGroup;
import com.momentus.foundation.finitevalue.model.FiniteValue;
import com.momentus.foundation.finitevalue.service.FiniteValueService;
import com.momentus.foundation.generic.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class LookupService {

    @Autowired
    FiniteValueService finiteValueService ;

    @Autowired
    GenericService genericService;

    @Autowired
    GeneralMessages generalMessages;

    public Map<String,String> getFValuesforDropDown(String groupCode, Locale locale)
    {
        Map<String,String> retValues = new LinkedHashMap<>();
        List<FiniteValue> finiteValueList =finiteValueService.getFiniteValueByGroup(groupCode);
        if (!CollectionUtils.isEmpty(finiteValueList)) {
            for (FiniteValue finiteValue : finiteValueList) {
                retValues.put(finiteValue.getFvCode(),generalMessages.getMessage(finiteValue.getFvValue(),locale));
               // retValues.put(finiteValue.getFvCode(),finiteValue.getFvValue());
            }
        }
        return  retValues;
    }



}
