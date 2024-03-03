package com.project.orderservice.service;

import com.project.orderservice.dto.OrderLineItemsDto;
import com.project.orderservice.dto.OrderRequest;
import com.project.orderservice.event.OrderPlacedEvent;
import com.project.orderservice.model.Order;
import com.project.orderservice.model.OrderLineItems;
import com.project.orderservice.repository.OrderRepository;
import com.project.orderservice.responses.InventoryResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @Transactional
    public Order placeOrder(OrderRequest orderRequest, String auth) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        System.out.println(orderRequest.getOrderLineItemsDtoList().get(0).getName());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream()
                .map(this::mapToDto).toList();
        order.setProducts(orderLineItems);
        Order orderWithAvailability = checkProductAvailabilityAndCreateOrder(order,auth);
        orderRepository.save(orderWithAvailability);
       log.info("Purchase confirmation: {}", confirmPurchase(orderWithAvailability,auth));
       return order;
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        System.out.println(orderLineItemsDto.getName());
        System.out.println(orderLineItemsDto.getPrice());
        System.out.println(orderLineItemsDto.getQuantity());
        return new OrderLineItems(
                orderLineItemsDto.getName(),
                orderLineItemsDto.getPrice(),
                orderLineItemsDto.getQuantity()
        );
    }

    private Order checkProductAvailabilityAndCreateOrder(Order order,String auth) {
        List<OrderLineItems> checkedProducts = getAvailableProductsFromInventory(order.getProducts(),auth);
        order.setProducts(checkedProducts);
        return order;
    }

    private List<OrderLineItems> getAvailableProductsFromInventory(List<OrderLineItems> products,String auth) {
        HttpHeaders authHeader = new HttpHeaders();
        authHeader.add(HttpHeaders.AUTHORIZATION,auth);
        HttpEntity<List<OrderLineItems>> postRequest = new HttpEntity<>(products,authHeader);

        InventoryResponse inventoryResponse = new RestTemplate()
                .exchange("http://localhost:8080/products/selling", HttpMethod.POST, postRequest, InventoryResponse.class)
                .getBody();

        return (inventoryResponse != null) ? inventoryResponse.getProducts() : new ArrayList<>();
    }

    @Transactional
    public HttpStatus confirmPurchase(Order orderWithAvailability,String auth) {
        HttpHeaders authHeader = new HttpHeaders();
        System.out.println("auth" + auth);
        authHeader.add(HttpHeaders.AUTHORIZATION,auth);
        HttpEntity<Order> postRequest = new HttpEntity<>(orderWithAvailability,authHeader);

        return new RestTemplate()
                .exchange("http://localhost:8765/selling/submit-order", HttpMethod.POST, postRequest, HttpStatus.class)
                .getBody();
    }
}
