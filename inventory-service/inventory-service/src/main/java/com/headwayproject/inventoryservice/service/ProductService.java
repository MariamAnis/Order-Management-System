package com.headwayproject.inventoryservice.service;

import com.headwayproject.inventoryservice.dto.CategoryDto;
import com.headwayproject.inventoryservice.dto.ProductDto;

import com.headwayproject.inventoryservice.entity.Product;
import com.headwayproject.inventoryservice.repository.ProductRepository;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
public class ProductService {
    @Autowired
  ProductRepository productRepository;
    @Autowired
    CategoryService categoryService;



    public void addProduct(ProductDto productDto){

        Product product = new Product();
        product.setName(productDto.getName());
        product.setCategory(productDto.getCategory());
        product.setQuantity(productDto.getQuantity());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        productRepository.save(product);
    }
    public List<ProductDto> getAllProducts(){
        List<Product> products =productRepository.findAll();
       return mapToDto(products);
    }

    public ProductDto getProductById(int id){

       Optional<Product> product =  productRepository.findById(id);
       return  product.map(value-> new ProductDto(value.getId(),value.getName(),value.getDescription(),value.getCategory()
       ,value.getPrice(),value.getQuantity() ,value.getImage())).orElse(null);

    }

    public void deleteById(int id){
      Optional<Product> product = productRepository.findById(id);
      if(product.isPresent()){
          productRepository.deleteById(id);
      }


    }

    public void editById(int id, ProductDto productDto){
      Optional<Product> productToEdit = productRepository.findById(id);
       if(productToEdit.isPresent()){
           productToEdit.get().setCategory(productDto.getCategory());
           productToEdit.get().setPrice(productDto.getPrice());
           productToEdit.get().setName(productDto.getName());
           productToEdit.get().setQuantity(productDto.getQuantity());
           productToEdit.get().setDescription(productDto.getDescription());
           productToEdit.get().setImage(productDto.getImage());
         productRepository.save((productToEdit.get()));
       }





    }

    public ProductDto getProductByName(String name) {
        Optional<Product> product =  productRepository.findByName(name);
        return  product.map(value-> new ProductDto(value.getId(),value.getName(),value.getDescription(),value.getCategory()
                ,value.getPrice(),value.getQuantity(), value.getImage())).orElse(null);
    }

    public void increaseQuantityWhenBuying(ProductDto product) {
        Optional<Product> productToEdit = productRepository.findByName(product.getName());
        if (productToEdit.isPresent()) {
            productToEdit.get().setCategory(productToEdit.get().getCategory());
            productToEdit.get().setPrice(product.getPrice());
            productToEdit.get().setName(product.getName());
            productToEdit.get().setDescription(product.getDescription());
            productToEdit.get().setQuantity(product.getQuantity()+productToEdit.get().getQuantity());
            productRepository.save(productToEdit.get());

        }
    }
    public void deductQuantityWhenSelling(ProductDto product) {
        Optional<Product> productToEdit = productRepository.findByName(product.getName());
        if (productToEdit.isPresent()) {
            productToEdit.get().setPrice(product.getPrice());
            productToEdit.get().setName(product.getName());
            productToEdit.get().setQuantity(productToEdit.get().getQuantity()-product.getQuantity());
            productRepository.save(productToEdit.get());

        }
    }

    public List<ProductDto> mapToDto(List<Product> products){
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product:products) {
            productDtoList.add(new ProductDto(product.getId(),
                    product.getName(),product.getDescription()
                    ,product.getCategory(),product.getPrice()
                    ,product.getQuantity(),product.getImage()));

        }
        return productDtoList;
    }


    public List<ProductDto> addNewlyPurchasedItems(List<ProductDto> products){
        for (ProductDto value : products) {
            ProductDto product = getProductByName(value.getName());
            if (product!=null) {
                {
                    increaseQuantityWhenBuying(value);
                    value.setCategory(product.getCategory());
                }
            } else {
                CategoryDto category = categoryService.findByName(value.getCategory().getName());
                if (category==null){
                    categoryService.addCategory(new CategoryDto(value.getCategory().getName()));
                }
                else{
                    value.getCategory().setName(category.getName());
                    value.getCategory().setId(category.getId());
                }
                addProduct(value);
            }
        }
        return products;
    }

    public List<ProductDto> checkAvailabilityOfOrderedItems(List<ProductDto> products){
        products.forEach(product -> {
            ProductDto productInStock = getProductByName(product.getName());
            if(productInStock!=null){
                if (productInStock.getQuantity() > 0) {
                    deductQuantityWhenSelling(product);
//                    product.setId(productInStock.getId());
//                    product.setCategory(productInStock.getCategory());
                } else {
                    products.remove(product);
                }
            }
        });
        return products;
    }





}


