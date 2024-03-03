package com.giza.purshasingmanagement.service.selling;

import com.giza.purshasingmanagement.controller.selling.SellingController;
import com.giza.purshasingmanagement.controller.selling.response.RevenueSummaryResponse;
import com.giza.purshasingmanagement.dto.selling.RevenueDTO;
import com.giza.purshasingmanagement.dto.selling.SellingProductDTO;
import com.giza.purshasingmanagement.dto.selling.SellingPurchaseDTO;
import com.giza.purshasingmanagement.entity.selling.ProductRevenue;
import com.giza.purshasingmanagement.repository.selling.RevenueRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class RevenueServiceImpl implements RevenueService {
    private final Logger logger = LoggerFactory.getLogger(SellingController.class);

    private final RevenueRepository revenueRepository;

    @Override
    public List<ProductRevenue> findAll() {
        return revenueRepository.findAll();
    }

    @Override
    public ProductRevenue findByName(String name) {
        Optional<ProductRevenue> repoResult = revenueRepository.findById(name);
        ProductRevenue productRevenue = null;
        if (repoResult.isPresent())
            productRevenue = repoResult.get();
        return productRevenue;
    }

    @Override
    public void save(SellingProductDTO product) {
        ProductRevenue productRevenue = findByName(product.getName());
        if (productRevenue == null){
            productRevenue = new ProductRevenue();
            productRevenue.setProductName(product.getName());
        }
        increasePurchase(productRevenue, product.getQuantity(), product.getPrice());
        revenueRepository.save(productRevenue);
    }

    void increasePurchase(ProductRevenue productRevenue, int purchaseCount, float price) {
        productRevenue.setPurchaseCount(purchaseCount + productRevenue.getPurchaseCount());
        productRevenue.setRevenue(productRevenue.getRevenue() + price * purchaseCount);
    }

    @Override
    public RevenueSummaryResponse getRevenueSummary() {
        RevenueSummaryResponse response = new RevenueSummaryResponse();
        List<ProductRevenue> revenues = findAll();
        List<RevenueDTO> revenueDTOs = new ArrayList<>();
        revenues.forEach(r -> revenueDTOs.add(RevenueDTO.entityToDTO(r)));
        response.setProductsRevenues(revenueDTOs);
        logger.info("Found " + response.getProductsPurchasedCount()
                + " product(s) purchased, with a total revenue of " + response.getTotalRevenue());
        return response;
    }

    /** Creating purchase record by processing the order id, date and products **/
    @Override
    public void submitPurchase(SellingPurchaseDTO purchase) {
        purchase.getProducts().forEach(product -> {
            save(product);
            logger.info("Increased " + product.getQuantity() + " to product: " + product.getName());
        });
    }
}
