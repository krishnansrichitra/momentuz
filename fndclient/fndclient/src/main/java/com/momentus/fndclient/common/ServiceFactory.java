package com.momentus.fndclient.common;

import com.momentus.fndclient.purchase.service.PurchaseOrderService;
import com.momentus.foundation.generic.service.GenericService;
import com.momentus.foundation.generic.service.IServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceFactory implements IServiceFactory {

    @Autowired
    GenericService genericService;

    @Autowired
    PurchaseOrderService purchaseOrderService;

    @Override
    public GenericService getService(String entity) {
        if ("Purchase Order".equalsIgnoreCase(entity))
            return purchaseOrderService ;
        return genericService;
    }
}
