package com.momentus.fndclient.customer.service;

import com.momentus.fndclient.customer.model.Customer;
import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.transaction.MomentusError;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.generic.dao.GenericDAO;
import com.momentus.foundation.generic.service.MapToEntityMapper;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;

@Service
public class CustomerService {
    @Autowired
    GenericDAO genericDAO;
    @Autowired
    MapToEntityMapper mapToEntityMapper;

    @Autowired
    GeneralMessages generalMessages;

    @Transactional
    public TransactionResponse createEntity(Map<String, Object> dataMap, OrgBasedEntity entity, ApplicationContext context) {
        TransactionResponse transactionResponse = new TransactionResponse();
        if (context.getOrganization().getId() != 1L) {
            entity.setOrgId(context.getOrganization());
        }

        this.mapToEntityMapper.populateFromMap(dataMap, entity, context);
        if (entity.getBK().get("name") == null) {
            transactionResponse.addMomentusError( new MomentusError("20001",
                    generalMessages.getMessage("20001", Locale.US)));
            transactionResponse.setResponseStatus(TransactionResponse.RESPONSE_STATUS.FAILURE);
            return transactionResponse;


        }

        entity.setCreatedBy(context.getLoggedInUser());
        entity.setCreatedTime(LocalDateTime.now());
        this.genericDAO.create(entity);
        return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);
    }
}
