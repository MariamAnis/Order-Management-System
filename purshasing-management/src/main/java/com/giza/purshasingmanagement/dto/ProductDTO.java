package com.giza.purshasingmanagement.dto;

import com.giza.purshasingmanagement.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable {
    private String name;
    private int quantity;
    private float price;

    static public ProductDTO entityToDto(Product entity) {
        ProductDTO dto = new ProductDTO();
        dto.name = entity.getName();
        dto.quantity = entity.getQuantity();
        dto.price = entity.getPrice();
        return dto;
    }
}
