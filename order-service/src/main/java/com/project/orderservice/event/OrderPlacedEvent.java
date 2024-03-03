package com.project.orderservice.event;

import com.project.orderservice.dto.OrderLineItemsDto;
import com.project.orderservice.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent {

    private String orderNumber;
    private String message;
    private String status;
    private Order order;
    private List<OrderLineItemsDto> orderLineItemsDtoList;
    private String email;
}
