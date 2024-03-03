package com.giza.purshasingmanagement.entity.selling;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "product_revenue")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class ProductRevenue implements Serializable {

    @Id
    @Column(name = "product_name")
    private String productName;

    @Column(name = "purchase_count")
    private long purchaseCount;

    @Column(name = "revenue")
    private double revenue;
}
