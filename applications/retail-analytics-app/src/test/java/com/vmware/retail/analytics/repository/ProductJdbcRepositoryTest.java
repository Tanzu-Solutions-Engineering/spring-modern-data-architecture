package com.vmware.retail.analytics.repository;

import com.vmware.retail.analytics.repository.jdbc.ProductJdbcRepository;
import com.vmware.retail.domain.CustomerFavorites;
import com.vmware.retail.domain.Product;
import com.vmware.retail.domain.order.ProductOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class ProductJdbcRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private ProductJdbcRepository subject;

    @BeforeEach
    void setUp() {
        subject = new ProductJdbcRepository(jdbcTemplate,namedParameterJdbcTemplate);
    }

    @Test
    void given_validCustomer_then_find_then_return_expected() {

        String customerId = "hello";
        int topCount = 3;
        CustomerFavorites expected = CustomerFavorites.builder().id(customerId).favorites(new TreeSet<>()).build();

        var actual = subject.findCustomerFavoritesByCustomerIdAndTopCount(customerId,topCount);

        assertEquals(expected, actual);
        verify(jdbcTemplate).query(anyString(),
                any(RowCallbackHandler.class),
                anyString(), //customerId
                any()); //topCount
    }

    @Test
    void given_products_when_findFrequently_then_return_products() {

        Product product = new Product("id","name");
        List<Product> expected = asList(product);

        when(namedParameterJdbcTemplate.query(anyString(),anyMap(),any(RowMapper.class))).thenReturn(expected);

        List<ProductOrder> productOrders = asList(new ProductOrder("id",3));
        var actual = subject.findFrequentlyBoughtTogether(productOrders);

        verify(namedParameterJdbcTemplate).query(anyString(),any(Map.class),any(RowMapper.class));
    }

    @Test
    void given_products_when_save_then_save_to_database() {
        Product product = new Product("id","name");
        List<Product> expected = asList(product);

        this.subject.saveProducts(expected);

        verify(namedParameterJdbcTemplate).batchUpdate(anyString(),any(Map[].class));
    }
}