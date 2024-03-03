package com.giza.purshasingmanagement.service.selling;

import com.giza.purshasingmanagement.Utils;
import com.giza.purshasingmanagement.controller.selling.response.PurchaseDetailsResponse;
import com.giza.purshasingmanagement.controller.selling.response.SellItemsResponse;
import com.giza.purshasingmanagement.dto.buying.BuyingProductDTO;
import com.giza.purshasingmanagement.dto.buying.OrderDTO;
import com.giza.purshasingmanagement.dto.selling.SellingPurchaseDTO;
import com.giza.purshasingmanagement.entity.Product;
import com.giza.purshasingmanagement.entity.selling.SellingPurchase;
import com.giza.purshasingmanagement.repository.selling.SellingRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class SellingServiceImpl implements SellingService {
    private final Logger logger = LoggerFactory.getLogger(SellingServiceImpl.class);

    private final RevenueService revenueService;
    private final SellingRepository sellingRepository;

    @Override
    public List<SellingPurchase> findAll() {
        return sellingRepository.findAll();
    }

    @Override
    public SellingPurchase findByPurchaseId(long id) {
        Optional<SellingPurchase> repoResult = sellingRepository.findById(id);
        SellingPurchase purchase = null;
        if (repoResult.isPresent())
            purchase = repoResult.get();
        return purchase;
    }

    @Override
    public SellingPurchase findByOrderId(long id) {
        if (id <= 0)
            return null;
        List<SellingPurchase> purchaseList = findAll();
        SellingPurchase purchase = null;
        for (SellingPurchase p : purchaseList)
            if (p.getOrderId() == id) {
                purchase = p;
                break;
            }
        return purchase;
    }

    @Override
    public SellingPurchase save(SellingPurchase purchase) {
        return sellingRepository.save(purchase);
    }

    @Override
    public SellItemsResponse checkAndSubmitOrder(OrderDTO order) {
        Utils.checkOrderValidity(order);
        SellingPurchase purchase = createPurchaseRecord(order);
        SellItemsResponse response = new SellItemsResponse();
        response.setPurchase(SellingPurchaseDTO.entityToDTO(purchase));
        response.setMessage("Successful Order Purchase");
        if (response.getPurchase() != null)
            revenueService.submitPurchase(response.getPurchase());
        return response;
    }

    /** Creating purchase record by processing the order id, date and products **/
    private SellingPurchase createPurchaseRecord(OrderDTO order) {
        Map<String, Pair<Integer, Float>> uniqueProductMap = order.getProducts().stream().collect(
                Collectors.toMap(
                        BuyingProductDTO::getName,
                        p -> new Pair<>(p.getQuantity(), p.getPrice()),
                        (p1, p2) -> new Pair<>(p1.a + p2.a, (p1.a * p1.b + p2.a * p2.b) / (p1.a + p2.a))
                )
        );

        SellingPurchase purchase = new SellingPurchase();
        purchase.setOrderId(order.getId());
        purchase.setPurchaseDate(new Date(System.currentTimeMillis()));
        List<Product> products = new ArrayList<>();
        double revenue = 0;
        for (Map.Entry<String, Pair<Integer, Float>> entry : uniqueProductMap.entrySet()) {
            String name = entry.getKey();
            int quantity = entry.getValue().a;
            float price = entry.getValue().b;
            products.add(new Product(name, quantity, price));
            revenue += quantity * price;
        }
        purchase.setProducts(products);
        purchase.setRevenue(revenue);
        return save(purchase);
    }

    @Override
    public PurchaseDetailsResponse getPurchaseDetails() {
        List<SellingPurchase> purchaseList = findAll();
        List<SellingPurchaseDTO> purchaseListDTO = new ArrayList<>();
        purchaseList.forEach(p -> purchaseListDTO.add(SellingPurchaseDTO.entityToDTO(p)));
        PurchaseDetailsResponse response = new PurchaseDetailsResponse();
        response.setPurchaseList(purchaseListDTO);
        return response;
    }
}
