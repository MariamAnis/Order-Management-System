package com.giza.purshasingmanagement.entity;

import com.giza.purshasingmanagement.entity.buying.BuyingPurchase;
import com.giza.purshasingmanagement.entity.selling.SellingPurchase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private int quantity;
    private float price;

    public Product(String name, int quantity, float price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    @ManyToOne(targetEntity = SellingPurchase.class)
    @JoinColumn(name = "selling_purchase_id")
    private SellingPurchase sellingPurchase;

    @ManyToOne(targetEntity = BuyingPurchase.class)
    @JoinColumn(name = "buying_purchase_id")
    private BuyingPurchase buyingPurchase;
}
