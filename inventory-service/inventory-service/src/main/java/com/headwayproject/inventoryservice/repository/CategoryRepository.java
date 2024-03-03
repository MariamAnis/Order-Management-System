package com.headwayproject.inventoryservice.repository;

import com.headwayproject.inventoryservice.dto.CategoryDto;
import com.headwayproject.inventoryservice.entity.Category;
import com.headwayproject.inventoryservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    public Optional<Category> findByName(String name);
    public Optional<Category> findById(int id);
}
