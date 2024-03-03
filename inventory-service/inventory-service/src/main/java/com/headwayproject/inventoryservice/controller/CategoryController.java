package com.headwayproject.inventoryservice.controller;

import com.headwayproject.inventoryservice.dto.CategoryDto;

import com.headwayproject.inventoryservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    @GetMapping
    public List<CategoryDto> getAll(){
       return categoryService.findAll();

    }


    @GetMapping("/{id}")
    public CategoryDto findById(@PathVariable int id){
     return categoryService.findById(id);
    }



    @PostMapping
    public ResponseEntity<String> addCategory(@RequestBody CategoryDto categoryDto){
        categoryService.addCategory(categoryDto);
        return ResponseEntity.ok().body("{\"message\": \"Category is added\"}");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id){
        categoryService.delete(id);
        return ResponseEntity.ok().body("{\"message\": \"Category is deleted\"}");

    }


}
