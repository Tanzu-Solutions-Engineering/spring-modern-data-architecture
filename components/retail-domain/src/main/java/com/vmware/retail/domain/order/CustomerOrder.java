package com.vmware.retail.domain.order;

import com.vmware.retail.domain.customer.CustomerIdentifier;

public record CustomerOrder (Long id,
                             CustomerIdentifier customerIdentifier,
                             ProductOrder[] productOrders) {
}
