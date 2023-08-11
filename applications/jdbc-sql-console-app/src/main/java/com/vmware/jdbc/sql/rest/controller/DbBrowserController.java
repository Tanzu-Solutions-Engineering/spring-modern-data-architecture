/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.jdbc.sql.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import nyla.solutions.core.patterns.jdbc.Sql;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * REST endpoint to browser database details
 * @author gregory green
 */
@RestController
@RequestMapping("database")
@RequiredArgsConstructor
public class DbBrowserController {
    private final JdbcTemplate jdbcTemplate;
    private final Sql sql;

    @SneakyThrows
    @GetMapping("schemas")
    public List<Map<String,?>> listSchemas() {

        var dataSource = jdbcTemplate.getDataSource();
        List<Map<String,?>> list = new ArrayList<>();

        try(var connection = dataSource.getConnection()){

            try(var resultSet = connection.getMetaData().getCatalogs())
            {
                while(resultSet.next())
                {
                    list.add(sql.toMap(resultSet));
                }
            }

            return list;
        }
    }

    @SneakyThrows
    @GetMapping("tables")
    public List<Map<String, ?>> listTables(@RequestParam(required=false) String catalog,
                                           @RequestParam(required=false) String schemaPattern,
                                           @RequestParam(required=false) String tableNamePattern,
                                           @RequestParam(required=false) String[] types) {
        var dataSource = jdbcTemplate.getDataSource();
        List<Map<String,?>> list = new ArrayList<>();

        try(var connection = dataSource.getConnection()){

            try(var resultSet = connection.getMetaData().getTables(catalog,schemaPattern,tableNamePattern,types))
            {
                while(resultSet.next())
                {
                    list.add(sql.toMap(resultSet));
                }
            }

            return list;
        }
    }
}
