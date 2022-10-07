package com.vmware.retail.analytics.repository;

import com.vmware.retail.domain.CustomerFavorites;

public interface ProductRepository {
    CustomerFavorites findCustomerFavoritesByCustomerIdAndTopCount(String customerId,int topCount);
}
