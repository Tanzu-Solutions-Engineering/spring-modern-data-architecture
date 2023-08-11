/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.jdbc.sql.rest.controller;

import nyla.solutions.core.patterns.jdbc.Sql;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DbBrowserControllerTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private Sql sql;

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private DatabaseMetaData databaseMetaData;

    @Mock
    private ResultSet resultSet;

    private DbBrowserController subject;
    private String catalog ="catalog";
    private String schemaPattern ="schema";
    private String tableNamePattern = "%";
    private String[] types = {};

    @BeforeEach
    void setUp() {
        subject = new DbBrowserController(jdbcTemplate,sql);
    }

    @Test
    void listTables() throws SQLException {

        when(jdbcTemplate.getDataSource()).thenReturn(dataSource);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.getMetaData()).thenReturn(databaseMetaData);
        when(databaseMetaData.getTables(catalog,schemaPattern,tableNamePattern,types)).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        var actual = subject.listTables(catalog,schemaPattern,tableNamePattern,types);
        assertNotNull(actual);


    }

    @Test
    void listSchemas() throws SQLException {

        when(jdbcTemplate.getDataSource()).thenReturn(dataSource);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.getMetaData()).thenReturn(databaseMetaData);
        when(databaseMetaData.getCatalogs()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        List<Map<String, ?>> expected = asList(new HashMap<>());

        List<Map<String, ?>> actual = subject.listSchemas();

        assertEquals(expected, actual);

    }
}