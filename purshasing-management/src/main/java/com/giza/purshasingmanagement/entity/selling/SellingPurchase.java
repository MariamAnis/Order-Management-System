package com.giza.purshasingmanagement.entity.selling;

import com.giza.purshasingmanagement.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "selling-purchase")
public class SellingPurchase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "purchase_id")
    private long purchaseId;

    @Column(name = "order_id")
    private long orderId;

    @Column(name = "purchase_date")
    private Date purchaseDate;

    @Column(name = "revenue")
    private double revenue;

    @Column(name = "products")
    @ElementCollection(targetClass = Product.class)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "selling_purchase_id")
    private List<Product> products;
}
