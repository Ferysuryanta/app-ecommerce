package com.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carts")
public class Carts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;

    @ManyToOne
    @JoinColumn(name = "product_variant_id")
    private ProductVariants productVariant;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}
