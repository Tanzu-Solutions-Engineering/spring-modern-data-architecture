package com.vmware.retail.analytics.service.impl;

import com.vmware.retail.analytics.repository.CustomerFavoriteRepository;
import com.vmware.retail.analytics.repository.ProductRepository;
import com.vmware.retail.domain.Product;
import com.vmware.retail.domain.Promotion;
import com.vmware.retail.domain.customer.CustomerIdentifier;
import com.vmware.retail.domain.order.CustomerOrder;
import com.vmware.retail.domain.order.ProductOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerAnalyticsDataServiceTest {

    @Mock
    private CustomerFavoriteRepository customerFavoriteRepository;
    @Mock
    private ProductRepository productRepository;

    @Mock
    private RedisTemplate<String, Promotion> redisTemplate;
    private CustomerAnalyticsDataService subject;

    private final int top3 = 3;

    @BeforeEach
    void setUp() {
        subject = new CustomerAnalyticsDataService(customerFavoriteRepository, productRepository, redisTemplate, top3);
    }

    @Test
    void given_customer_when_accept_then_cacheFavorites() {
        var customerId = "u01";

        var subject = new CustomerAnalyticsDataService(customerFavoriteRepository, productRepository, redisTemplate, top3);
        var customIdentifier = new CustomerIdentifier(customerId);

        subject.constructFavorites(customIdentifier);

        verify(customerFavoriteRepository).save(any());
        verify(productRepository).findCustomerFavoritesByCustomerIdAndTopCount(customerId, top3);
    }

    @Test
    void given_customerOrder_getRecommendations_based_onOrders_thenPublish_Recommendations() {

        Product expectedProduct = new Product("id","name");
        List<Product> expectedProducts = asList(expectedProduct);
        String expectedId = "customerId";

        Promotion expected = new Promotion(expectedId,null,expectedProducts);

        Long orderId = 3L;
        String productId = "pId";
        int quantity = 3;
        String customerId = "customerId";
        ProductOrder productOrder = new ProductOrder(productId,quantity);
        CustomerIdentifier customerIdentifier = new CustomerIdentifier(customerId);
        List<ProductOrder> productOrders = asList(productOrder);
        var customerOrder = new CustomerOrder(orderId,customerIdentifier,productOrders);


        when(productRepository.findFrequentlyBoughtTogether(any())).thenReturn(expectedProducts);

        var actual = subject.publishPromotion(customerOrder);

        assertEquals(expected, actual);

        verify(productRepository).findFrequentlyBoughtTogether(customerOrder.productOrders());
        verify(redisTemplate).convertAndSend(anyString(),any(Promotion.class));

    }
}
