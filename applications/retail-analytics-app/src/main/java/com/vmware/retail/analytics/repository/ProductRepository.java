package com.vmware.retail.analytics.repository;

import com.vmware.retail.domain.CustomerFavorites;
import com.vmware.retail.domain.Product;
import com.vmware.retail.domain.order.ProductOrder;

import java.util.List;

public interface ProductRepository {
    CustomerFavorites findCustomerFavoritesByCustomerIdAndTopCount(String customerId,int topCount);

    List<Product> findFrequentlyBoughtTogether(List<ProductOrder> productOrders);
}
