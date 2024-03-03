package com.giza.purshasingmanagement.dto.selling;

import com.giza.purshasingmanagement.entity.selling.SellingPurchase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class SellingPurchaseDTO implements Serializable {

    private long orderId;
    private Date purchaseDate;
    private List<SellingProductDTO> products;

    private double revenue;

    static public SellingPurchaseDTO entityToDTO(SellingPurchase entity) {
        SellingPurchaseDTO dto = new SellingPurchaseDTO();
        dto.orderId = entity.getOrderId();
        dto.revenue = entity.getRevenue();
        dto.purchaseDate = entity.getPurchaseDate();
        List<SellingProductDTO> products = new ArrayList<>();
        if (entity.getProducts() != null)
            entity.getProducts().forEach( product -> products.add(SellingProductDTO.entityToDTO(product)));
        dto.products = products;
        return dto;
    }
}
