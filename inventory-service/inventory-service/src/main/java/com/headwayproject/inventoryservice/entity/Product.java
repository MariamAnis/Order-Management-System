package com.headwayproject.inventoryservice.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id ;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

   @ManyToOne
   @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "price")
    private float price;

    @Column(name = "quantity")
    private int quantity;

    @Column(name="image")
    private String image;

}
