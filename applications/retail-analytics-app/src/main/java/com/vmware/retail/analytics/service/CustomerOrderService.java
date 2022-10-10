package com.vmware.retail.analytics.service;

import com.vmware.retail.domain.order.CustomerOrder;

import javax.transaction.Transactional;

public interface CustomerOrderService {
    void saveOrder(CustomerOrder customerOrder);
}
