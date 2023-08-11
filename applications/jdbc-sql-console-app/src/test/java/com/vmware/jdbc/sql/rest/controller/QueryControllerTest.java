/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.jdbc.sql.rest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static nyla.solutions.core.util.Organizer.toMap;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueryControllerTest {

    private String sql = "";

    @Mock
    private JdbcTemplate jdbcTemplate;
    private QueryController subject;

    @BeforeEach
    void setUp() {
        subject = new QueryController(jdbcTemplate);
    }

    @Test
    void sql() {
        List<Map<String, Object>> expected = asList(
                toMap("Hello1","World"),
                toMap("Hello1","World")
        );

        when(jdbcTemplate.queryForList(anyString())).thenReturn(expected);

        var actual = subject.query(sql);

        assertEquals(expected, actual);
    }
}