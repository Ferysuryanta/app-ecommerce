package com.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_shipping_address")
public class OrderShippingAddresses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "shipping_address_id", nullable = false)
    private ShippingAddresses shippingAddress;

    @Column(name = "full_address", nullable = false)
    private String fullAddress;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

}
