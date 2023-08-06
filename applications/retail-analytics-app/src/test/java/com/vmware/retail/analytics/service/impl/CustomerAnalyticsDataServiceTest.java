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
import org.junit.jupiter.api.DisplayName;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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

    private Product expectedProduct = new Product("id","name");
    private List<Product> expectedProducts = asList(expectedProduct);
    private String expectedId = "customerId";
    private Promotion expected = new Promotion(expectedId,null,expectedProducts);

    private Long orderId = 3L;
    private String productId = "pId";
    private int quantity = 3;
    private String customerId = "customerId";
    private ProductOrder productOrder = new ProductOrder(productId,quantity);
    private CustomerIdentifier customerIdentifier = new CustomerIdentifier(customerId);
    private List<ProductOrder> productOrders = asList(productOrder);
    private CustomerOrder customerOrder = new CustomerOrder(orderId,customerIdentifier,productOrders);

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

        when(productRepository.findFrequentlyBoughtTogether(any())).thenReturn(expectedProducts);

        var actual = subject.publishPromotion(customerOrder);

        assertEquals(expected, actual);

        verify(productRepository).findFrequentlyBoughtTogether(customerOrder.productOrders());
        verify(rabbitTemplate).convertAndSend(anyString(),anyString(),any(Promotion.class));

    }

    @DisplayName("GIVEN No products find sold together WHEN publishPromotion THEN verify no data sent")
    @Test
    void doNotPublishWhenNoRecommendations() {
        var actual = subject.publishPromotion(customerOrder);

        assertNull(actual);

        verify(productRepository).findFrequentlyBoughtTogether(customerOrder.productOrders());
        verify(rabbitTemplate,never()).convertAndSend(anyString(),anyString(),any(Promotion.class));

    }
}
