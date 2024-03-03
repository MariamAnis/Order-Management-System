package com.giza.purshasingmanagement.controller.buying.response;

import com.giza.purshasingmanagement.dto.buying.BuyingPurchaseDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BuyItemsResponse {
    private BuyingPurchaseDTO purchase;
    private String inventoryMessage;
}
