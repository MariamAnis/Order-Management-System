package com.giza.purshasingmanagement.service.buying;

import com.giza.purshasingmanagement.Utils;
import com.giza.purshasingmanagement.controller.buying.response.BuyItemsResponse;
import com.giza.purshasingmanagement.controller.buying.response.InventoryStockResponse;
import com.giza.purshasingmanagement.controller.buying.response.PurchaseDetailsResponse;
import com.giza.purshasingmanagement.dto.buying.BuyingProductDTO;
import com.giza.purshasingmanagement.dto.buying.BuyingPurchaseDTO;
import com.giza.purshasingmanagement.dto.buying.OrderDTO;
import com.giza.purshasingmanagement.entity.Product;
import com.giza.purshasingmanagement.entity.buying.BuyingPurchase;
import com.giza.purshasingmanagement.repository.buying.BuyingRepository;
import lombok.Getter;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

import static com.giza.purshasingmanagement.AppConstants.INVENTORY_ADD_PRODUCTS;
import static com.giza.purshasingmanagement.AppConstants.INVENTORY_BASE_URL;

@Service
@RequiredArgsConstructor
public class BuyingServiceImpl implements BuyingService {

    private final BuyingRepository buyingRepository;
    private final CostService costService;
    private final RestTemplate restTemplate;

    @Override
    public BuyItemsResponse checkAndSubmitOrder(OrderDTO order, String auth) {
        Utils.checkOrderValidity(order);
        HttpHeaders authHeader = new HttpHeaders();
        authHeader.add(HttpHeaders.AUTHORIZATION,auth);
        HttpEntity<List<BuyingProductDTO>> postRequest = new HttpEntity<>(order.getProducts(),authHeader);
        ResponseEntity<InventoryStockResponse> inventoryResponse = restTemplate.postForEntity(
                INVENTORY_BASE_URL + INVENTORY_ADD_PRODUCTS,
                postRequest,
                InventoryStockResponse.class);

        BuyItemsResponse response = new BuyItemsResponse();

        if (inventoryResponse.getStatusCode() == HttpStatus.OK) {
            BuyingPurchase purchase;
            if (inventoryResponse.getBody() != null && inventoryResponse.getBody().getProducts() != null) {
                List<BuyingProductDTO> products = inventoryResponse.getBody().getProducts();
                purchase = createPurchaseRecord(products);
            } else
                purchase = new BuyingPurchase();

            response.setPurchase(BuyingPurchaseDTO.entityToDTO(purchase));
            response.setInventoryMessage("Successfully added to Inventory");
            save(purchase);

            if (response.getPurchase() != null)
                costService.submitPurchase(response.getPurchase());
        }
        else if (inventoryResponse.getStatusCode() == HttpStatus.BAD_REQUEST)
            response.setInventoryMessage("Bad Request to Inventory");
        else
            response.setInventoryMessage("Internal Server Error from Inventory");
        return response;
    }

    @Override
    public PurchaseDetailsResponse getPurchaseDetails() {
        List<BuyingPurchase> purchaseList = findAll();
        PurchaseDetailsResponse response = new PurchaseDetailsResponse();
        List<BuyingPurchaseDTO> purchaseDTOs = new ArrayList<>();
        purchaseList.forEach(p -> purchaseDTOs.add(BuyingPurchaseDTO.entityToDTO(p)));
        response.setPurchaseList(purchaseDTOs);
        return response;
    }

    /** Creating purchase record by processing the incoming inventory products added **/
    private BuyingPurchase createPurchaseRecord(List<BuyingProductDTO> inventoryProducts) {
        Map<String, Pair<Integer, Float>> uniqueProductMap = inventoryProducts.stream()
                .collect(
                        Collectors.toMap(
                                BuyingProductDTO::getName,
                                p -> new Pair<>(p.getQuantity(), p.getPrice()),
                                (p1, p2) -> new Pair<>(p1.a + p2.a, (p1.a * p1.b + p2.a * p2.b) / (p1.a + p2.a))
                        )
                );
        BuyingPurchase purchase = new BuyingPurchase();
        purchase.setPurchaseDate(new Date(System.currentTimeMillis()));
        List<Product> products = new ArrayList<>();
        double cost = 0;
        for (Map.Entry<String, Pair<Integer, Float>> entry : uniqueProductMap.entrySet()) {
            String name = entry.getKey();
            int quantity = entry.getValue().a;
            float price = entry.getValue().b;
            products.add(new Product(name, quantity, price));
            cost += quantity * price;
        }
        purchase.setProducts(products);
        purchase.setCost(cost);
        return save(purchase);
    }

    @Override
    public List<BuyingPurchase> findAll() {
        return buyingRepository.findAll();
    }

    @Override
    public BuyingPurchase findByPurchaseId(long id) {
        Optional<BuyingPurchase> repoResult = buyingRepository.findById(id);
        BuyingPurchase purchase = null;
        if (repoResult.isPresent())
            purchase = repoResult.get();
        return purchase;
    }

    @Override
    public BuyingPurchase save(BuyingPurchase purchase) {
        return buyingRepository.save(purchase);
    }
}
