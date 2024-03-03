package com.giza.purshasingmanagement.dto.buying;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class OrderDTO implements Serializable {

    private long id;
    private LocalDateTime purchaseTime;
    private List<BuyingProductDTO> products;
}

