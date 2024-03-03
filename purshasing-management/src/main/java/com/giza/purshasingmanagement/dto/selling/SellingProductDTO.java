package com.giza.purshasingmanagement.dto.selling;

import com.giza.purshasingmanagement.entity.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class SellingProductDTO implements Serializable {
    private String name;
    private int quantity;
    private float price;

    static public SellingProductDTO entityToDTO(Product entity) {
        SellingProductDTO dto = new SellingProductDTO();
        dto.name = entity.getName();
        dto.price = entity.getPrice();
        dto.quantity = entity.getQuantity();
        return dto;
    }
}
