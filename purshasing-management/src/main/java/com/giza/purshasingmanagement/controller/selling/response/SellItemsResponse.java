package com.giza.purshasingmanagement.controller.selling.response;

import com.giza.purshasingmanagement.dto.selling.SellingPurchaseDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SellItemsResponse {
    private SellingPurchaseDTO purchase;
    private String message;
}
