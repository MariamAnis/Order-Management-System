package com.giza.purshasingmanagement;
import com.giza.purshasingmanagement.controller.buying.BuyingController;
import com.giza.purshasingmanagement.controller.buying.response.BuyItemsResponse;
import com.giza.purshasingmanagement.controller.buying.response.InventoryStockResponse;
import com.giza.purshasingmanagement.controller.buying.response.PurchaseDetailsResponse;
import com.giza.purshasingmanagement.controller.selling.SellingController;
import com.giza.purshasingmanagement.controller.selling.response.SellItemsResponse;
import com.giza.purshasingmanagement.dto.buying.BuyingProductDTO;
import com.giza.purshasingmanagement.dto.buying.BuyingPurchaseDTO;
import com.giza.purshasingmanagement.dto.buying.OrderDTO;
import com.giza.purshasingmanagement.dto.selling.SellingProductDTO;
import com.giza.purshasingmanagement.dto.selling.SellingPurchaseDTO;
import com.giza.purshasingmanagement.entity.Product;
import com.giza.purshasingmanagement.service.buying.BuyingService;
import com.giza.purshasingmanagement.service.selling.SellingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.giza.purshasingmanagement.AppConstants.INVENTORY_ADD_PRODUCTS;
import static com.giza.purshasingmanagement.AppConstants.INVENTORY_BASE_URL;
import static com.giza.purshasingmanagement.common.TestHelper.buildValidMockOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

    @Autowired
    private BuyingController buyingController;
    @Autowired
    private SellingController sellingController;

    @MockBean
    BuyingService buyingService;
    @MockBean
    SellingService sellingService;
    @Mock
    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    void submitOrderInventoryErrorTest() {
        OrderDTO order = buildValidMockOrder();
        when(restTemplate.postForEntity(
                INVENTORY_BASE_URL + INVENTORY_ADD_PRODUCTS, order.getProducts(), InventoryStockResponse.class)
        ).thenReturn(ResponseEntity.internalServerError().body(null));

        ResponseEntity<BuyItemsResponse> actualResponse = buyingController.submitOrder(order, "test");

        BuyItemsResponse expectedResponse = new BuyItemsResponse();
        expectedResponse.setInventoryMessage("Internal Server Error from Inventory");

        assertEquals(HttpStatus.ACCEPTED, actualResponse.getStatusCode());
        if (actualResponse.getBody() != null)
            assertEquals(expectedResponse.getInventoryMessage(), actualResponse.getBody().getInventoryMessage());
    }

    @Test
    void submitOrderInventoryBadRequestTest() {
        OrderDTO order = buildValidMockOrder();
        when(restTemplate.postForEntity(
                INVENTORY_BASE_URL + INVENTORY_ADD_PRODUCTS, order.getProducts(), InventoryStockResponse.class)
        ).thenReturn(ResponseEntity.badRequest().body(null));

        ResponseEntity<BuyItemsResponse> actualResponse = buyingController.submitOrder(order,"test");

        BuyItemsResponse expectedResponse = new BuyItemsResponse();
        expectedResponse.setInventoryMessage("Bad Request to Inventory");

        assertEquals(HttpStatus.ACCEPTED, actualResponse.getStatusCode());
        if (actualResponse.getBody() != null)
            assertEquals(expectedResponse.getInventoryMessage(), actualResponse.getBody().getInventoryMessage());
    }

    @Test
    void submitBuyingOrderTest() {
        OrderDTO order = buildValidMockOrder();
        InventoryStockResponse inventoryResponse = new InventoryStockResponse();
        inventoryResponse.setProducts(order.getProducts());
        when(restTemplate.postForEntity(
                INVENTORY_BASE_URL + INVENTORY_ADD_PRODUCTS, order.getProducts(), InventoryStockResponse.class)
        ).thenReturn(ResponseEntity.ok().body(inventoryResponse));

        ResponseEntity<BuyItemsResponse> actualResponse = buyingController.submitOrder(order, "test");

        BuyItemsResponse expectedResponse = new BuyItemsResponse();
        expectedResponse.setInventoryMessage("Successfully added to Inventory");

        assertEquals(HttpStatus.ACCEPTED, actualResponse.getStatusCode());
        if (actualResponse.getBody() != null)
            assertEquals(expectedResponse.getInventoryMessage(), actualResponse.getBody().getInventoryMessage());
    }

    @Test
    void getBuyingPurchaseDetailsEmptyTest() {
        List<BuyingPurchaseDTO> purchaseDTOList = new ArrayList<>();
        PurchaseDetailsResponse expectedResponse = new PurchaseDetailsResponse();
        expectedResponse.setPurchaseList(purchaseDTOList);
        when(buyingService.getPurchaseDetails()).thenReturn(expectedResponse);

        ResponseEntity<PurchaseDetailsResponse> actualResponse = buyingController.getPurchaseDetails();
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        if (actualResponse.getBody() != null) {
            assertEquals(expectedResponse.getPurchaseCount(), actualResponse.getBody().getPurchaseCount());
            assertEquals(expectedResponse.getTotalCost(), actualResponse.getBody().getTotalCost());
            assertEquals(expectedResponse.getPurchaseList(), actualResponse.getBody().getPurchaseList());
        }
    }

    @Test
    void getBuyingPurchaseDetailsNonEmptyTest() {
        List<BuyingPurchaseDTO> purchaseDTOList = new ArrayList<>();

        BuyingPurchaseDTO purchase = new BuyingPurchaseDTO();
        List<BuyingProductDTO> products = new ArrayList<>();
        products.add(new BuyingProductDTO("Milk", 5, 20.0f));
        products.add(new BuyingProductDTO("Meat", 10, 100.0f));
        purchase.setProducts(products);

        purchaseDTOList.add(purchase);
        purchaseDTOList.add(purchase);

        PurchaseDetailsResponse expectedResponse = new PurchaseDetailsResponse();
        expectedResponse.setPurchaseList(purchaseDTOList);
        when(buyingService.getPurchaseDetails()).thenReturn(expectedResponse);

        ResponseEntity<PurchaseDetailsResponse> actualResponse = buyingController.getPurchaseDetails();
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        if (actualResponse.getBody() != null) {
            assertEquals(expectedResponse.getPurchaseCount(), actualResponse.getBody().getPurchaseCount());
            assertEquals(expectedResponse.getTotalCost(), actualResponse.getBody().getTotalCost());
            assertEquals(expectedResponse.getPurchaseList(), actualResponse.getBody().getPurchaseList());
        }
    }



    @Test
    void getSellingPurchaseDetailsEmptyTest() {
        List<SellingPurchaseDTO> purchaseDTOList = new ArrayList<>();
        com.giza.purshasingmanagement.controller.selling.response.PurchaseDetailsResponse expectedResponse = new com.giza.purshasingmanagement.controller.selling.response.PurchaseDetailsResponse();
        expectedResponse.setPurchaseList(purchaseDTOList);
        when(sellingService.getPurchaseDetails()).thenReturn(expectedResponse);

        ResponseEntity<com.giza.purshasingmanagement.controller.selling.response.PurchaseDetailsResponse> actualResponse = sellingController.getPurchaseDetails();
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        if (actualResponse.getBody() != null) {
            assertEquals(expectedResponse.getPurchaseCount(), actualResponse.getBody().getPurchaseCount());
            assertEquals(expectedResponse.getTotalRevenue(), actualResponse.getBody().getTotalRevenue());
            assertEquals(expectedResponse.getPurchaseList(), actualResponse.getBody().getPurchaseList());
        }
    }

    @Test
    void getSellingPurchaseDetailsNonEmptyTest() {
        List<SellingPurchaseDTO> purchaseDTOList = new ArrayList<>();

        SellingPurchaseDTO purchase = new SellingPurchaseDTO();
        List<SellingProductDTO> products = new ArrayList<>();
        products.add(SellingProductDTO.entityToDTO(new Product("Milk", 5, 20.0f)));
        products.add(SellingProductDTO.entityToDTO(new Product("Meat", 10, 100.0f)));
        purchase.setProducts(products);

        purchaseDTOList.add(purchase);
        purchaseDTOList.add(purchase);

        com.giza.purshasingmanagement.controller.selling.response.PurchaseDetailsResponse expectedResponse = new com.giza.purshasingmanagement.controller.selling.response.PurchaseDetailsResponse();
        expectedResponse.setPurchaseList(purchaseDTOList);
        when(sellingService.getPurchaseDetails()).thenReturn(expectedResponse);

        ResponseEntity<com.giza.purshasingmanagement.controller.selling.response.PurchaseDetailsResponse> actualResponse = sellingController.getPurchaseDetails();
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        if (actualResponse.getBody() != null) {
            assertEquals(expectedResponse.getPurchaseCount(), actualResponse.getBody().getPurchaseCount());
            assertEquals(expectedResponse.getTotalRevenue(), actualResponse.getBody().getTotalRevenue());
            assertEquals(expectedResponse.getPurchaseList(), actualResponse.getBody().getPurchaseList());
        }
    }
}