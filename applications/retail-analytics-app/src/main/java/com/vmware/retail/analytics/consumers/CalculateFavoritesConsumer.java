package com.vmware.retail.analytics.consumers;

import com.vmware.retail.analytics.repository.ProductRepository;
import com.vmware.retail.domain.CustomerFavorites;
import com.vmware.retail.domain.customer.CustomerIdentifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import org.springframework.data.redis.core.RedisTemplate;

@Component
public class CalculateFavoritesConsumer implements Consumer<CustomerIdentifier> {

        private final RedisTemplate<String, CustomerFavorites> redisTemplate;
        private final ProductRepository productRepository;
        private final int topCount;

    public CalculateFavoritesConsumer(RedisTemplate<String, CustomerFavorites> redisTemplate,
                                      ProductRepository productRepository,
                                      @Value("${customer.favorites.top.count:3}")
                                      int topCount) {
        this.redisTemplate = redisTemplate;
        this.productRepository = productRepository;
        this.topCount = topCount;
    }

    @Override
    public void accept(CustomerIdentifier customerIdentifier) {
        var customerId = customerIdentifier.customerId();
        var customerFavorites = productRepository.findCustomerFavoritesByCustomerIdAndTopCount(customerId, topCount);

         redisTemplate.opsForValue().set(customerId,customerFavorites);
    }
}
