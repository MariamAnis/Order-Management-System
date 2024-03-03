package com.giza.purshasingmanagement.controller.selling.response;

import com.giza.purshasingmanagement.dto.selling.RevenueDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class RevenueSummaryResponse {
    private List<RevenueDTO> productsRevenues;
    private int productsPurchasedCount;
    private double totalRevenue;

    public void setProductsRevenues(List<RevenueDTO> productsRevenues) {
        this.productsRevenues = productsRevenues;
        this.productsPurchasedCount = productsRevenues.size();
        this.totalRevenue = 0;
        productsRevenues.forEach(pRevenue -> totalRevenue += pRevenue.getRevenue());
    }
}
