package com.giza.purshasingmanagement.controller.buying.response;

import com.giza.purshasingmanagement.dto.buying.BuyingPurchaseDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PurchaseDetailsResponse {

    private List<BuyingPurchaseDTO> purchaseList;
    private int purchaseCount;
    private double totalCost;

    public void setPurchaseList(List<BuyingPurchaseDTO> purchaseList) {
        this.purchaseList = purchaseList;
        this.purchaseCount = purchaseList.size();
        this.totalCost = 0;
        for (BuyingPurchaseDTO purchase : purchaseList)
            totalCost += purchase.getCost();
    }
}
