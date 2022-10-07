package com.vmware.retail.analytics.entity;

import com.vmware.retail.domain.order.ProductOrder;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public record CustomerOrderEntity(
        @Id
        Long id,

        String customerId,

        @Embedded
        ProductOrder productQuantity) {
}
