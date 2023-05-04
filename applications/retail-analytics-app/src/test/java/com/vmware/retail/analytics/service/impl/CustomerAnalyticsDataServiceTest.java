package com.vmware.retail.analytics.service.impl;

import com.vmware.retail.analytics.repository.ProductRepository;
import com.vmware.retail.domain.CustomerFavorites;
import com.vmware.retail.domain.Product;
import com.vmware.retail.domain.ProductQuantity;
import com.vmware.retail.domain.Promotion;
import com.vmware.retail.domain.customer.CustomerIdentifier;
import com.vmware.retail.domain.order.CustomerOrder;
import com.vmware.retail.domain.order.ProductOrder;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerAnalyticsDataServiceTest {

    @Mock
    private RabbitTemplate rabbitTemplate;
    @Mock
    private ProductRepository productRepository;

    @Mock
    private CustomerAnalyticsDataService subject;


    private String promotionExchange = "promotions";
    private String customerFavoritesExchange = "favorites";
    private final int top3 = 3;
    private CustomerFavorites favorites;
    private String id = "id";
    private SortedSet<ProductQuantity> favoriteSet = new TreeSet<>();
    private ProductQuantity productQuantity = JavaBeanGeneratorCreator
                        .of(ProductQuantity.class).create();

    @BeforeEach
    void setUp() {

        favoriteSet.add(productQuantity);
        favorites = new CustomerFavorites(id,favoriteSet);

        subject = new CustomerAnalyticsDataService(
                rabbitTemplate,
                productRepository,
                top3,
                customerFavoritesExchange,
                promotionExchange);

    }

    @Test
    void given_customer_when_accept_then_cacheFavorites() {
        var customerId = "u01";

        when(productRepository.findCustomerFavoritesByCustomerIdAndTopCount(anyString(), anyInt())).thenReturn(favorites);
        var customIdentifier = new CustomerIdentifier(customerId);

        subject.constructFavorites(customIdentifier);

        verify(rabbitTemplate).convertAndSend(anyString(),anyString(),any(CustomerFavorites.class));
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
        verify(rabbitTemplate).convertAndSend(anyString(),anyString(),any(Promotion.class));

    }
}
