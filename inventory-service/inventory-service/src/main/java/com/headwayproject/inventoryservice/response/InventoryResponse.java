package com.headwayproject.inventoryservice.response;

import com.headwayproject.inventoryservice.dto.ProductDto;
import com.headwayproject.inventoryservice.entity.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
public class InventoryResponse {
    private List<ProductDto> products;

}
