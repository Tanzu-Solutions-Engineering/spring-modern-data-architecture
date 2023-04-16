/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.analytics.service;

import com.vmware.retail.domain.order.CustomerOrder;

public interface CustomerOrderService {
    void saveOrder(CustomerOrder customerOrder);
}
