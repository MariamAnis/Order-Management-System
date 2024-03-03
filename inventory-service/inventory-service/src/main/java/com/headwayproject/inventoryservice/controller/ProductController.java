package com.headwayproject.inventoryservice.controller;



import com.headwayproject.inventoryservice.dto.ProductDto;

import com.headwayproject.inventoryservice.response.InventoryResponse;
import com.headwayproject.inventoryservice.service.CategoryService;
import com.headwayproject.inventoryservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
   CategoryService categoryService;


    @GetMapping()
    public List<ProductDto> getAll(){
       return productService.getAllProducts();

    }

    @GetMapping("/{id}")
    public ProductDto findProduct(@PathVariable int id){
      return productService.getProductById(id);
    }
    @GetMapping("/product/{name}")
    public ProductDto findProductByName(@PathVariable String name){
       return productService.getProductByName(name);

    }


    @PostMapping("/purchased")
    public ResponseEntity<InventoryResponse> addPurchasedItems(@RequestBody List<ProductDto> products){
        InventoryResponse response = new InventoryResponse();
        response.setProducts(productService.addNewlyPurchasedItems(products));
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/selling")
    public InventoryResponse checkItemsAvailability(@RequestBody List<ProductDto> products){
        List<ProductDto> productDto = productService.checkAvailabilityOfOrderedItems(products);
        InventoryResponse inventoryResponse = new InventoryResponse();
        inventoryResponse.setProducts(productDto);
        return inventoryResponse;
    }
    @PostMapping
    public ResponseEntity<String> saveProduct(@RequestBody ProductDto product){
        productService.addProduct(product);
        return ResponseEntity.ok().body("{\"message\": \"Product is added\"}");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        productService.deleteById(id);
        return ResponseEntity.ok().body("{\"message\": \"Product is deleted\"}");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editProduct(@PathVariable int id, @RequestBody ProductDto product){
        productService.editById(id,product);
        return ResponseEntity.ok().body("{\"message\": \"Product is updated\"}");
    }
}
