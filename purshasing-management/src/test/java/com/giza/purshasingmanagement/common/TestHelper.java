package com.giza.purshasingmanagement.common;

import com.giza.purshasingmanagement.dto.buying.BuyingProductDTO;
import com.giza.purshasingmanagement.dto.buying.OrderDTO;

import java.util.ArrayList;
import java.util.List;

public abstract class TestHelper {

    public static OrderDTO buildMockEmptyOrder() {
        return new OrderDTO();
    }

    public static OrderDTO buildMockOrder() {
        OrderDTO order = new OrderDTO();
        order.setProducts(new ArrayList<>());
        return order;
    }

    public static OrderDTO buildValidMockOrder() {
        OrderDTO order = new OrderDTO();
        List<BuyingProductDTO> products = new ArrayList<>();

        products.add(buildValidProduct());
        products.add(buildValidProduct());
        order.setProducts(products);
        return order;
    }

    public static BuyingProductDTO buildValidProduct() {
        BuyingProductDTO product = new BuyingProductDTO();
        product.setName("Milk");
        product.setQuantity(5);
        product.setPrice(100f);
        return product;
    }
}
