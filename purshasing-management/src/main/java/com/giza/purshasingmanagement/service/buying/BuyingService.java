package com.giza.purshasingmanagement.service.buying;

import com.giza.purshasingmanagement.controller.buying.response.BuyItemsResponse;
import com.giza.purshasingmanagement.controller.buying.response.PurchaseDetailsResponse;
import com.giza.purshasingmanagement.dto.buying.OrderDTO;
import com.giza.purshasingmanagement.entity.buying.BuyingPurchase;

import java.util.List;

public interface BuyingService {

    List<BuyingPurchase> findAll();

    BuyingPurchase findByPurchaseId(long id);

    BuyingPurchase save(BuyingPurchase purchase);

    BuyItemsResponse checkAndSubmitOrder(OrderDTO order, String auth);

    PurchaseDetailsResponse getPurchaseDetails();
}
