package com.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    @ManyToOne
    @JoinColumn(name = "product_variant_id")
    private ProductVariants productVariant;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "color")
    private String color;

    @Column(name = "size")
    private String size;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "total_amount", nullable = false)
    private float totalAmount;
}
