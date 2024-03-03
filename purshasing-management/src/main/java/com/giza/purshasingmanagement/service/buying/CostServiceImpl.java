package com.giza.purshasingmanagement.service.buying;

import com.giza.purshasingmanagement.controller.buying.response.CostSummaryResponse;
import com.giza.purshasingmanagement.dto.buying.BuyingProductDTO;
import com.giza.purshasingmanagement.dto.buying.BuyingPurchaseDTO;
import com.giza.purshasingmanagement.dto.buying.CostDTO;
import com.giza.purshasingmanagement.entity.Product;
import com.giza.purshasingmanagement.entity.buying.ProductCost;
import com.giza.purshasingmanagement.repository.buying.CostRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class CostServiceImpl implements CostService {
    private final Logger logger = LoggerFactory.getLogger(CostServiceImpl.class);

    private final CostRepository costRepository;

    @Override
    public List<ProductCost> findAll() {
        return costRepository.findAll();
    }

    @Override
    public ProductCost findByName(String id) {
        Optional<ProductCost> repoResult = costRepository.findById(id);
        ProductCost productCost = null;
        if (repoResult.isPresent())
            productCost = repoResult.get();
        return productCost;
    }

    @Override
    public void save(BuyingProductDTO product) {
        ProductCost productCost = findByName(product.getName());
        if (productCost == null){
            productCost = new ProductCost();
            productCost.setProductName(product.getName());
        }
        increasePurchase(productCost, product.getQuantity(), product.getPrice());
        costRepository.save(productCost);
    }

    private void increasePurchase(ProductCost productCost, int purchaseCount, float price) {
        productCost.setPurchaseCount(purchaseCount + productCost.getPurchaseCount());
        productCost.setCost(productCost.getCost() + purchaseCount * price);
    }

    /** Creating purchase record by processing the order id, date and products **/
    @Override
    public void submitPurchase(BuyingPurchaseDTO purchase) {
        purchase.getProducts().forEach(product -> {
            save(product);
            logger.info("Bought " + product.getQuantity() + " of product " + product.getName());
        });
    }

    @Override
    public CostSummaryResponse getCostSummary() {
        CostSummaryResponse response = new CostSummaryResponse();
        List<ProductCost> costs = findAll();
        List<CostDTO> costDTOs = new ArrayList<>();
        costs.forEach(c -> costDTOs.add(CostDTO.entityToDTO(c)));
        response.setProductsCosts(costDTOs);
        logger.info("Found " + response.getProductsPurchasedCount()
                + " product(s) purchased, with a total cost of " + response.getTotalCost());
        return response;
    }
}
