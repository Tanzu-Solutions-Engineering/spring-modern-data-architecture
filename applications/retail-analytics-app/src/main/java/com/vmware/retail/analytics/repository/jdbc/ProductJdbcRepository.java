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
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.patterns.jdbc.Sql;
import nyla.solutions.core.util.Organizer;
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
@RequiredArgsConstructor
@Slf4j
public class ProductJdbcRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    private double confidence;


    @Override
    public CustomerFavorites findCustomerFavoritesByCustomerIdAndTopCount(String customerId, int topCount) {

        final String sql = """
            SELECT data, total_quantity
            from products p, 
               (SELECT sum(quantity) total_quantity, 
                    product_id 
            FROM customer_orders 
            WHERE customer_id = ? 
            GROUP BY product_id order by total_quantity 
            DESC 
            FETCH FIRST ? rows only) top_orders
            WHERE p.id = top_orders.product_id;
            """;

        final SortedSet<ProductQuantity> productQuantities = new TreeSet<>();

        jdbcTemplate.query(sql, rs -> {
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

        //Notes In causes have limits depending on database
        // Postgres does set an exact count limit

        final String sql = """
            select distinct p.data
            --,top_associations.original_SKU,top_associations.bought_with,top_associations.times_bought_together, count_by_product.product_cnt, cast(top_associations.times_bought_together as double precision)/cast(count_by_product.product_cnt as  double precision) as probability
                from (
                SELECT c.original_SKU as original_SKU, c.bought_with as bought_with, count(*) as times_bought_together
                FROM (
                  SELECT a.product_id as original_SKU, b.product_id as bought_with
                  FROM customer_orders a
                  INNER join customer_orders b
                  ON a.order_id = b.order_id AND a.product_id != b.product_id ) c
                GROUP BY c.original_SKU, c.bought_with
                having original_SKU in (:productIds)  and bought_with not in (:productIds)
                ORDER BY times_bought_together desc
                FETCH FIRST 10 rows only) top_associations,
                (select product_id, sum(quantity) as product_cnt
                      from customer_orders
                      group by product_id) count_by_product,
                products p
                where count_by_product.product_id = top_associations.original_SKU
                and cast(top_associations.times_bought_together as double precision)/
                cast(count_by_product.product_cnt as  double precision) > :confidence
                and  p.id = top_associations.bought_with
                """;


        Map<String, ?> map = toMap("productIds", productOrders.stream().map( po -> po.productId()).toList(),
                "confidence",confidence);

        log.info(" Sql: {} productOrders: {} confidence > {} ",sql,productOrders,confidence);


        RowMapper<Product> rowMapper = (rs , rowNum) -> {
            try {
                return objectMapper.readValue(rs.getString("data"),Product.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };

        return this.namedParameterJdbcTemplate.query(sql,map, rowMapper);
    }

    @SneakyThrows
    @Override
    public void saveProducts(List<Product> products) {

        final var insertSql = """
                INSERT INTO products(id, data) 
                VALUES (:id,to_json(:data::json))
                ON CONFLICT (id)
                DO
                   UPDATE SET data = to_json(:data::json);
                """;

        log.info("sql {} inputs: {}",insertSql,products);

        Map<String, ?>[] maps = new Map[products.size()];

        int i = 0;
        for (Product p: products) {
            maps[i++] = toMap("id",p.id(),"data", objectMapper.writeValueAsString(p));
        }

        namedParameterJdbcTemplate.batchUpdate(insertSql,maps);
    }

}
