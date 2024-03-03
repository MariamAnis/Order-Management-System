package com.giza.purshasingmanagement.repository.buying;

import com.giza.purshasingmanagement.entity.buying.BuyingPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyingRepository extends JpaRepository<BuyingPurchase, Long> {
}
