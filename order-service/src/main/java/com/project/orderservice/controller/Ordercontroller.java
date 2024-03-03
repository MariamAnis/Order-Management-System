package com.project.orderservice.controller;
import com.project.orderservice.config.OrderProducer;
import com.project.orderservice.dto.OrderRequest;
import com.project.orderservice.event.OrderPlacedEvent;
import com.project.orderservice.model.Order;
import com.project.orderservice.model.Token;
import com.project.orderservice.responses.InventoryResponse;
import com.project.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class Ordercontroller {

    //now we need to call this order service from the controller
    @Autowired
    private final OrderService orderService;
    @Autowired
    private  final OrderProducer orderProducer;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest request, @RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        try {
            System.out.println(auth);
            Order order = orderService.placeOrder(request,auth);
            String url = "http://localhost:8765/api/v1/auth/user/" + auth.substring(7);;
            String email = new RestTemplate().postForEntity(url,auth, String.class)
                    .getBody();

            System.out.println(request.getOrderLineItemsDtoList());
            System.out.println(email);
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
            orderPlacedEvent.setOrderNumber(order.getOrderNumber());
            orderPlacedEvent.setEmail(email);
            orderPlacedEvent.setStatus("Pending");
            orderPlacedEvent.setMessage("Order is pending");
            orderPlacedEvent.setOrderLineItemsDtoList(request.getOrderLineItemsDtoList());
            orderProducer.sendMessage(orderPlacedEvent);
           return ResponseEntity.ok().body("{\"message\": \"order placed successfully\"}");
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
          return  ResponseEntity.ok().body("{\"message\": \"order placed successfully\"}"+e);
        }
    }

}
