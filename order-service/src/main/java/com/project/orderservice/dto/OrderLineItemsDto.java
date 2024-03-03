package com.project.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Data
@AllArgsConstructor


public class OrderLineItemsDto {
    private int id;
    private String name;
    private BigDecimal price;
    private int quantity;
    private String image;
    private String description;



}
