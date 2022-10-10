package com.vmware.retail.analytics.consumers;

import com.vmware.retail.analytics.service.CustomerAnalyticService;
import com.vmware.retail.domain.customer.CustomerIdentifier;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public record CalculateFavoritesConsumer (CustomerAnalyticService customerAnalyticService)  implements Consumer<CustomerIdentifier> {

    @Override
    public void accept(CustomerIdentifier customerIdentifier) {
        customerAnalyticService.constructFavorites(customerIdentifier);
    }

}
