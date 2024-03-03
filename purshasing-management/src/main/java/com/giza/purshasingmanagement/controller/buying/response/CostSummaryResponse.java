package com.giza.purshasingmanagement.controller.buying.response;

import com.giza.purshasingmanagement.dto.buying.CostDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CostSummaryResponse {

    private List<CostDTO> productsCosts;
    private int productsPurchasedCount;
    private double totalCost;

    public void setProductsCosts(List<CostDTO> productsCosts) {
        this.productsCosts = productsCosts;
        this.productsPurchasedCount = productsCosts.size();
        this.totalCost = 0;
        productsCosts.forEach(pRevenue -> totalCost += pRevenue.getCost());
    }
}
