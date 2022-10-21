package com.vmware.retail.analytics.entity;

import com.vmware.retail.domain.ProductQuantity;
import com.vmware.retail.domain.order.ProductOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "customer_orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerOrderEntity
{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        private Long orderId;

        private String customerId;

        @Embedded
        private ProductOrderEntity productOrder;
}
