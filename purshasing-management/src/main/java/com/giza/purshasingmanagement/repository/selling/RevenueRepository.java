package com.giza.purshasingmanagement.repository.selling;

import com.giza.purshasingmanagement.entity.selling.ProductRevenue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevenueRepository extends JpaRepository<ProductRevenue, String> {
}
