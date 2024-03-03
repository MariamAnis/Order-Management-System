package com.giza.purshasingmanagement.dto.buying;

import com.giza.purshasingmanagement.entity.buying.BuyingPurchase;
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
public class BuyingPurchaseDTO implements Serializable {
    private Date purchaseDate;
    private List<BuyingProductDTO> products;
    private double cost;

    static public BuyingPurchaseDTO entityToDTO(BuyingPurchase entity) {
        BuyingPurchaseDTO dto = new BuyingPurchaseDTO();
        dto.purchaseDate = entity.getPurchaseDate();
        dto.cost = entity.getCost();
        List<BuyingProductDTO> products = new ArrayList<>();
        if (entity.getProducts() != null)
            entity.getProducts().forEach( product -> products.add(BuyingProductDTO.entityToDTO(product)));
        dto.products = products;
        return dto;
    }
}