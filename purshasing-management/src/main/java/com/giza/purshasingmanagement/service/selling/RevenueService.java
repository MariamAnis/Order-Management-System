package com.giza.purshasingmanagement.service.selling;

import com.giza.purshasingmanagement.controller.selling.response.RevenueSummaryResponse;
import com.giza.purshasingmanagement.dto.selling.SellingProductDTO;
import com.giza.purshasingmanagement.dto.selling.SellingPurchaseDTO;
import com.giza.purshasingmanagement.entity.selling.ProductRevenue;

import java.util.List;

public interface RevenueService {

    List<ProductRevenue> findAll();

    ProductRevenue findByName(String name);

    void save(SellingProductDTO product);

    RevenueSummaryResponse getRevenueSummary();

    void submitPurchase(SellingPurchaseDTO purchase);
}
