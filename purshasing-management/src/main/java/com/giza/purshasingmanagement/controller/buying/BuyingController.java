package com.giza.purshasingmanagement.controller.buying;

import com.giza.purshasingmanagement.controller.buying.response.BuyItemsResponse;
import com.giza.purshasingmanagement.controller.buying.response.CostSummaryResponse;
import com.giza.purshasingmanagement.controller.buying.response.PurchaseDetailsResponse;
import com.giza.purshasingmanagement.dto.buying.OrderDTO;
import com.giza.purshasingmanagement.service.buying.BuyingService;
import com.giza.purshasingmanagement.service.buying.CostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buying")
@Getter
@Setter
@RequiredArgsConstructor
public class BuyingController {
    // 11/11/2023 nice to have use @controllerAdvice for exception and success handling https://www.baeldung.com/exception-handling-for-rest-with-spring https://www.baeldung.com/spring-security-exceptionhandler
    private final Logger logger = LoggerFactory.getLogger(BuyingController.class);

    private final CostService costService;
    private final BuyingService buyingService;

    @PostMapping("/submit-order")
    public ResponseEntity<BuyItemsResponse> submitOrder(@RequestBody OrderDTO order, @RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        logger.info("Order received");
        BuyItemsResponse response = buyingService.checkAndSubmitOrder(order, auth);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }



    @GetMapping("/get-purchase-details")
    public ResponseEntity<PurchaseDetailsResponse> getPurchaseDetails() {
        logger.info("Buying: Getting purchase details");
        PurchaseDetailsResponse response = buyingService.getPurchaseDetails();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get-cost-summary")
    public ResponseEntity<CostSummaryResponse> getCostSummary() {
        logger.info("Getting cost summary");
        CostSummaryResponse response = costService.getCostSummary();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
