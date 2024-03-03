package com.giza.purshasingmanagement.dto.selling;

import com.giza.purshasingmanagement.entity.selling.ProductRevenue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class RevenueDTO implements Serializable {

    private String productName;
    private long purchaseCount;
    private double revenue;

    static public RevenueDTO entityToDTO(ProductRevenue entity) {
        RevenueDTO dto = new RevenueDTO();
        dto.productName = entity.getProductName();
        dto.purchaseCount = entity.getPurchaseCount();
        dto.revenue = entity.getRevenue();
        return dto;
    }
}
