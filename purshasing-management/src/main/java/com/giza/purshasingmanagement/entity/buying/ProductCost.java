package com.giza.purshasingmanagement.entity.buying;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "product_cost")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class ProductCost implements Serializable {
    @Id
    @Column(name = "product_name", unique = true)
    private String productName;
    // Not wrong but not considered as best practice

    @Column(name = "purchase_count")
    private long purchaseCount;

    @Column(name = "cost")
    private double cost;
}
