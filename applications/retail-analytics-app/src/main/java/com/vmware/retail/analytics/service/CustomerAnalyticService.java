package com.vmware.retail.analytics.service;

import com.vmware.retail.domain.Promotion;
import com.vmware.retail.domain.customer.CustomerIdentifier;
import com.vmware.retail.domain.order.CustomerOrder;

public interface CustomerAnalyticService {
    void constructFavorites(CustomerIdentifier customerIdentifier);

    Promotion publishPromotion(CustomerOrder customerOrder);
}
