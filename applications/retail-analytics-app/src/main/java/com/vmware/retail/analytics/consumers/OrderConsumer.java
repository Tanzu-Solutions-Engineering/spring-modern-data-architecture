package com.vmware.retail.analytics.consumers;

import com.vmware.retail.analytics.repository.CustomerOrderRepository;
import com.vmware.retail.analytics.entity.CustomerOrderEntity;
import com.vmware.retail.domain.order.CustomerOrder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.function.Consumer;

@Component
public record OrderConsumer(CustomerOrderRepository repository) implements Consumer<CustomerOrder> {

    @Override
    public void accept(CustomerOrder customerOrder) {
        repository.saveAll(Arrays.stream(customerOrder.productOrders())
                .map(po -> new CustomerOrderEntity(
                        customerOrder.id(),
                        customerOrder.customerIdentifier().customerId(),
                        po
                )).toList());
    }
}
