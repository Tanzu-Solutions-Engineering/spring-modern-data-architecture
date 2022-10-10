package com.vmware.retail.domain.order;

import com.vmware.retail.domain.customer.CustomerIdentifier;

import java.util.List;

public record CustomerOrder (Long id,
                             CustomerIdentifier customerIdentifier,
                             List<ProductOrder> productOrders) {
}
