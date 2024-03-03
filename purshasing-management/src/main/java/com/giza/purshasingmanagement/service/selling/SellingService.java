package com.giza.purshasingmanagement.service.selling;

import com.giza.purshasingmanagement.controller.selling.response.PurchaseDetailsResponse;
import com.giza.purshasingmanagement.controller.selling.response.SellItemsResponse;
import com.giza.purshasingmanagement.dto.buying.OrderDTO;
import com.giza.purshasingmanagement.entity.selling.SellingPurchase;

import java.util.List;

public interface SellingService {

    List<SellingPurchase> findAll();

    SellingPurchase findByPurchaseId(long id);

    SellingPurchase findByOrderId(long id);

    SellingPurchase save(SellingPurchase purchase);

    PurchaseDetailsResponse getPurchaseDetails();

    SellItemsResponse checkAndSubmitOrder(OrderDTO order);
}
