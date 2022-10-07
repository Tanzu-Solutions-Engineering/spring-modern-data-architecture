package com.vmware.retail.analytics.repository;

import com.vmware.retail.domain.CustomerFavorites;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductJdbcRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Test
    void given_validCustomer_then_find_then_return_expected() {

        var subject = new ProductJdbcRepository(jdbcTemplate);
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
}