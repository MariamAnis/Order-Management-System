package com.giza.purshasingmanagement.controller.selling.response;

import com.giza.purshasingmanagement.dto.selling.SellingPurchaseDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PurchaseDetailsResponse {

    private List<SellingPurchaseDTO> purchaseList;
    private int purchaseCount;
    private double totalRevenue;

    public void setPurchaseList(List<SellingPurchaseDTO> purchaseList) {
        this.purchaseList = purchaseList;
        this.purchaseCount = purchaseList.size();
        this.totalRevenue = 0;
        for (SellingPurchaseDTO purchase : purchaseList)
            totalRevenue += purchase.getRevenue();
    }
}
