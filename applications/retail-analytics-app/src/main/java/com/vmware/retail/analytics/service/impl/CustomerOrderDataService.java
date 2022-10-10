package com.vmware.retail.analytics.service.impl;

import com.vmware.retail.analytics.entity.CustomerOrderEntity;
import com.vmware.retail.analytics.repository.CustomerOrderRepository;
import com.vmware.retail.analytics.service.CustomerAnalyticService;
import com.vmware.retail.analytics.service.CustomerOrderService;
import com.vmware.retail.domain.order.CustomerOrder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class CustomerOrderDataService implements CustomerOrderService {

    private final CustomerOrderRepository repository;
    private final CustomerAnalyticService customerAnalyticService;

    @Override
    @Transactional
    public void saveOrder(CustomerOrder customerOrder){
        repository.saveAll(customerOrder.productOrders().stream()
                .map(po -> new CustomerOrderEntity(
                        customerOrder.id(),
                        customerOrder.customerIdentifier().customerId(),
                        po
                )).toList());

        customerAnalyticService.constructFavorites(customerOrder.customerIdentifier());
        customerAnalyticService.publishPromotion(customerOrder);
    }
}
