package com.project.orderservice.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitOrderResponse {

    private Map<Long, Double> productRevenuePair;
    private long purchaseId;
    private String message;
}
