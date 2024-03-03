package com.giza.purshasingmanagement.dto.buying;

import com.giza.purshasingmanagement.entity.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class BuyingProductDTO implements Serializable {
    private int id;
    private String name;
    private int quantity;
    private float price;
    private CategoryDTO category;
    public BuyingProductDTO(String name, int quantity, float price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    static public BuyingProductDTO entityToDTO(Product entity) {
        BuyingProductDTO dto = new BuyingProductDTO();
        dto.name = entity.getName();
        dto.price = entity.getPrice();
        dto.quantity = entity.getQuantity();
        return dto;
    }
}
