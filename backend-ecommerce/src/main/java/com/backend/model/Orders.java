package com.backend.model;

import com.backend.util.OrderStatus;
import com.backend.util.PaymentStatus;
import com.backend.util.PaymentType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number", nullable = false, unique = true)
    private String orderNumber;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "total_amount", nullable = false)
    private float totalAmount;

    @Column(name = "discount_amount", nullable = false)
    private float discountAmount;

    @Column(name = "gross_amount", nullable = false)
    private float grossAmount;

    @Column(name = "shipping_amount", nullable = false)
    private float shippingAmount;

    @Column(name = "net_amount", nullable = false)
    private float netAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", nullable = false)
    private PaymentType paymentType;

    @Column(name = "payment_transaction_id", nullable = false)
    private String paymentTransactionId;
}
