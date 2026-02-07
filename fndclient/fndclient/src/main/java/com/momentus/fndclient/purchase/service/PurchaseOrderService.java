package com.momentus.fndclient.purchase.service;

import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.generic.service.GenericService;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PurchaseOrderService extends GenericService {



    @Override
    @Transactional
    public TransactionResponse createOrUpdateEntity(Map<String, Object> dataMap, OrgBasedEntity entity, ApplicationContext context) {
        return super.createOrUpdateEntity(dataMap, entity, context);
    }

    @Override
    protected TransactionResponse validate(OrgBasedEntity entity, ApplicationContext context, boolean skipBKValidation) {
        return super.validate(entity, context, skipBKValidation);
    }
}
