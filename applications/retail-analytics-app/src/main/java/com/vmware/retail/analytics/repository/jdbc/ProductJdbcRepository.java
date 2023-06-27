/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.analytics.repository.jdbc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmware.retail.analytics.repository.ProductRepository;
import com.vmware.retail.domain.CustomerFavorites;
import com.vmware.retail.domain.Product;
import com.vmware.retail.domain.ProductQuantity;
import com.vmware.retail.domain.order.ProductOrder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import static nyla.solutions.core.util.Organizer.toMap;

@Repository
@Slf4j
@ConditionalOnProperty(name = "greenplum",havingValue = "false",matchIfMissing = true)
public class ProductJdbcRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    private double confidence;


    private final String findCustomerFavoritesByCustomerIdAndTopCountSql;


    private final String frequentBoughtSql;


    private String insertSql;

    public ProductJdbcRepository(JdbcTemplate jdbcTemplate,
                                 NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                 @Value("${retail.frequent.bought.confidence:0}")
                                 double confidence,
                                 @Value("${retail.frequent.bought.sql}")
                                 String frequentBoughtSql,
                                 @Value("${retail.favorites.top.sql}")
                                 String findCustomerFavoritesByCustomerIdAndTopCountSql,
                                 @Value("${retail.product.save.sql}")
                                 String insertSql) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.confidence = confidence;
        this.frequentBoughtSql = frequentBoughtSql;
        this.findCustomerFavoritesByCustomerIdAndTopCountSql = findCustomerFavoritesByCustomerIdAndTopCountSql;
        this.insertSql = insertSql;
    }

    @Override
    public CustomerFavorites findCustomerFavoritesByCustomerIdAndTopCount(String customerId, int topCount) {

        final SortedSet<ProductQuantity> productQuantities = new TreeSet<>();

        jdbcTemplate.query(findCustomerFavoritesByCustomerIdAndTopCountSql, rs -> {
                    try {
                        var productJson = rs.getString("data");
                        var product = objectMapper.readValue(productJson, Product.class);
                        productQuantities.add(new ProductQuantity(product, rs.getInt("total_quantity")));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                },
                customerId,
                topCount);

        return new CustomerFavorites(customerId, productQuantities);
    }

    @Override
    public List<Product> findFrequentlyBoughtTogether(List<ProductOrder> productOrders) {

        Map<String, ?> map = toMap("productIds", productOrders.stream().map( po -> po.productId()).toList(),
                "confidence",confidence);

        log.info(" Sql: {} productOrders: {} confidence > {} ",frequentBoughtSql,productOrders,confidence);


        RowMapper<Product> rowMapper = (rs , rowNum) -> {
            try {
                return objectMapper.readValue(rs.getString("data"),Product.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };

        return this.namedParameterJdbcTemplate.query(frequentBoughtSql,map, rowMapper);
    }

    @SneakyThrows
    @Override
    public void saveProducts(List<Product> products) {

        log.info("sql {} inputs: {}",insertSql,products);

        Map<String, ?>[] maps = new Map[products.size()];

        int i = 0;
        for (Product p: products) {
            maps[i++] = toMap("id",p.id(),"data", objectMapper.writeValueAsString(p));
        }

        namedParameterJdbcTemplate.batchUpdate(insertSql,maps);
    }

}
