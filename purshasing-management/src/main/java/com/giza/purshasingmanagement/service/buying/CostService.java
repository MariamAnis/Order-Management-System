package com.giza.purshasingmanagement.service.buying;

import com.giza.purshasingmanagement.controller.buying.response.CostSummaryResponse;
import com.giza.purshasingmanagement.dto.buying.BuyingProductDTO;
import com.giza.purshasingmanagement.dto.buying.BuyingPurchaseDTO;
import com.giza.purshasingmanagement.entity.buying.ProductCost;
import com.giza.purshasingmanagement.entity.Product;

import java.util.List;
import java.util.Map;

public interface CostService {



    List<ProductCost> findAll();

    ProductCost findByName(String id);

    void save(BuyingProductDTO product);

    void submitPurchase(BuyingPurchaseDTO purchase);


    CostSummaryResponse getCostSummary();
}
