package com.giza.purshasingmanagement.repository.buying;

import com.giza.purshasingmanagement.entity.buying.ProductCost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostRepository extends JpaRepository<ProductCost, String> {
}
