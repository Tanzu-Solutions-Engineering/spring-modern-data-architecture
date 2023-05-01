/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.analytics.repository.jdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmware.retail.domain.Product;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static nyla.solutions.core.util.Organizer.toMap;

/**
 * @author gregory green
 */
@Repository
@Slf4j
@ConditionalOnProperty(name = "greenplum",havingValue = "true")
public class ProductGreenplumRepository extends ProductJdbcRepository {

    private ObjectMapper objectMapper = new ObjectMapper();

    private double confidence;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProductGreenplumRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);

        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @SneakyThrows
    @Override
    public void saveProducts(List<Product> products) {

        final var updateSql = """
                   UPDATE products SET data = to_json(:data::json)::jsonb
                   where id = :id;
                """;

        final var insertSql = """
                INSERT INTO products(id, data) 
                VALUES (:id,to_json(:data::json)::jsonb);
                """;

        log.info("sql {} inputs: {}",insertSql,products);


        int i = 0;
        for (Product p: products) {
            Map<String, ?> map = toMap("id",p.id(),"data", objectMapper.writeValueAsString(p));

            log.info("sql {} inputs: {}",updateSql,products);
            var cnt = this.namedParameterJdbcTemplate.update(updateSql,map);

            if(cnt == 0)
                this.namedParameterJdbcTemplate.update(insertSql,map);
        }

    }

}
