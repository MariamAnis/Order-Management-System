package com.headwayproject.inventoryservice.service;
import com.headwayproject.inventoryservice.dto.CategoryDto;
import com.headwayproject.inventoryservice.entity.Category;
import com.headwayproject.inventoryservice.repository.CategoryRepository;
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
public class CategoryService {

    @Autowired
     CategoryRepository categoryRepository;


    public List<CategoryDto> findAll(){
      List<Category> categories =  categoryRepository.findAll();
     return mapToDto(categories);
    }


    public void addCategory(CategoryDto categoryDto){

        Category category = new Category();
        category.setName(categoryDto.getName());
        categoryRepository.save(category);

    }
    public CategoryDto findById(int id){
       Optional<Category> category= categoryRepository.findById(id);
        return category.map(value -> new CategoryDto(value.getId(), value.getName())).orElse(null);

    }
    public CategoryDto findByName(String name){
        Optional<Category> category = categoryRepository.findByName(name);
        return category.map(value -> new CategoryDto(value.getId(), value.getName())).orElse(null);
    }

    public void delete (int id){
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            categoryRepository.deleteById(id);
        }
    }

    public List<CategoryDto> mapToDto(List<Category> categories){
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for (Category category:categories) {
            categoryDtoList.add(new CategoryDto(category.getId(),category.getName()));

        }
        return categoryDtoList;
    }

}
