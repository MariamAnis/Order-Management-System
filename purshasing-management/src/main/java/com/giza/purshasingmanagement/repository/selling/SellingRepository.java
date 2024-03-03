package com.giza.purshasingmanagement.repository.selling;

import com.giza.purshasingmanagement.entity.selling.SellingPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellingRepository extends JpaRepository<SellingPurchase, Long> {
}
