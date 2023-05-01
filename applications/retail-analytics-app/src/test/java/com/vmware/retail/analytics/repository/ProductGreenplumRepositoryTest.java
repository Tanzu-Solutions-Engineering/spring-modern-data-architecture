/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.analytics.repository;

import com.vmware.retail.analytics.repository.jdbc.ProductGreenplumRepository;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class ProductGreenplumRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private ProductGreenplumRepository subject;

    @BeforeEach
    void setUp() {
        subject = new ProductGreenplumRepository(jdbcTemplate,namedParameterJdbcTemplate);
    }

    @Test
    void given_products_when_save_then_save_to_database() {
        Product product = new Product("id","name");
        List<Product> expected = asList(product);

        this.subject.saveProducts(expected);

        verify(namedParameterJdbcTemplate,times(2)).update(anyString(),any(Map.class));
    }
}