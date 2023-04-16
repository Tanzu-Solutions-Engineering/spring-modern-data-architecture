/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.domain.order;

import com.vmware.retail.domain.customer.CustomerIdentifier;

import java.util.List;

public record CustomerOrder (Long id,
                             CustomerIdentifier customerIdentifier,
                             List<ProductOrder> productOrders) {
}
