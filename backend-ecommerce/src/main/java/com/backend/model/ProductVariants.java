package com.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_variants")
public class ProductVariants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    @Column(name = "color")
    private String color;

    @Column(name = "size")
    private String size;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;
}
