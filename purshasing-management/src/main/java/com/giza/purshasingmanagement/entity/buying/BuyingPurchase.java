package com.giza.purshasingmanagement.entity.buying;

import com.giza.purshasingmanagement.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "buying-purchase")
@Getter
@Setter
@RequiredArgsConstructor
public class BuyingPurchase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "purchase_id")
    private long purchaseId;

    @Column(name = "purchase_date")
    private Date purchaseDate;

    @Column(name = "cost")
    private double cost;

    @Column(name = "products")
    @ElementCollection(targetClass = Product.class)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "buying_purchase_id")
    private List<Product> products;
}
