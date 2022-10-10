package com.vmware.retail.analytics.entity;

import com.vmware.retail.domain.order.ProductOrder;

import javax.persistence.*;

@Entity
@Table(name = "customer_orders")
public record CustomerOrderEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id,
        Long orderId,
        String customerId,
        @Embedded
        ProductOrder productQuantity) {
    public CustomerOrderEntity(Long orderId, String customerId, ProductOrder po) {
        this(null, orderId, customerId, po);
    }
}
