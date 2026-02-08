package com.momentus.fndclient.purchase.service;

import com.momentus.fndclient.purchase.model.PurchaseOrder;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.model.BaseEntity;
import com.momentus.foundation.common.service.NextUpService;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.generic.service.GenericService;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PurchaseOrderService extends GenericService {

    @Autowired
    NextUpService nextUpService;


    @Override
    protected void preValidation(BaseEntity entity, ApplicationContext context) {
        PurchaseOrder purchaseOrder = (PurchaseOrder) entity;
        String docNo = nextUpService.getNextUpNo(context , "Purchase Order",null,"PO",null,null);
        purchaseOrder.setDocNumber(docNo);
        super.preValidation(entity, context);
    }

    @Override
    protected TransactionResponse validate(OrgBasedEntity entity, ApplicationContext context, boolean skipBKValidation) {
        return super.validate(entity, context, skipBKValidation);
    }
}
