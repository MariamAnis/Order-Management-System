package com.giza.purshasingmanagement.dto.buying;

import com.giza.purshasingmanagement.entity.buying.ProductCost;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CostDTO implements Serializable {

    private String productName;
    private long purchaseCount;
    private double cost;

    static public CostDTO entityToDTO(ProductCost entity) {
        CostDTO dto = new CostDTO();
        dto.productName = entity.getProductName();
        dto.purchaseCount = entity.getPurchaseCount();
        dto.cost = entity.getCost();
        return dto;
    }
}
