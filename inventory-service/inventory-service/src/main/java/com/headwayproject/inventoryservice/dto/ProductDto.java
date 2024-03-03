package com.headwayproject.inventoryservice.dto;

import com.headwayproject.inventoryservice.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
public class ProductDto {
    private int id ;


    private String name;


    private String description;


    private Category category;

    private float price;


    private int quantity;

    private String image;
}
