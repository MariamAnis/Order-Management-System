package com.giza.purshasingmanagement.common;

import com.giza.purshasingmanagement.Utils;
import com.giza.purshasingmanagement.dto.buying.BuyingProductDTO;
import com.giza.purshasingmanagement.dto.buying.OrderDTO;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static com.giza.purshasingmanagement.common.TestHelper.buildMockEmptyOrder;
import static com.giza.purshasingmanagement.common.TestHelper.buildValidProduct;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UtilsTests {

    @Test
    void checkNoProductsOrderTest() {
        assertThrows(ResponseStatusException.class, () -> {
            OrderDTO order = buildMockEmptyOrder();
            Utils.checkOrderValidity(order);
        });

        assertThrows(ResponseStatusException.class, () -> {
            OrderDTO order = buildMockEmptyOrder();
            order.setProducts(new ArrayList<>());
            Utils.checkOrderValidity(order);
        });
    }

    @Test
    void checkInvalidProducts() {
        assertThrows(ResponseStatusException.class, () -> {
            OrderDTO order = buildInvalidMockOrder();
            Utils.checkOrderValidity(order);
        });
    }

    private OrderDTO buildInvalidMockOrder() {
        OrderDTO order = new OrderDTO();
        List<BuyingProductDTO> products = new ArrayList<>();

        products.add(buildInvalidProduct());
        products.add(buildValidProduct());
        order.setProducts(products);
        return order;
    }

    private BuyingProductDTO buildInvalidProduct() {
        BuyingProductDTO product = new BuyingProductDTO();
        product.setName("Milk");
        product.setQuantity(-5);
        product.setPrice(100f);
        return product;
    }
}
