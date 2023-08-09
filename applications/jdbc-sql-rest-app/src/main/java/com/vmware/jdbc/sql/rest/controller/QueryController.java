/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.jdbc.sql.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * REST API to execute SQL queries
 * @author gregory green
 */
@RestController
@RequestMapping("query")
@RequiredArgsConstructor
public class QueryController {

    private final JdbcTemplate jdbcTemplate;

    @PostMapping
    public List<Map<String, Object>> query(@RequestBody String sql) {
        return jdbcTemplate.queryForList(sql);
    }
}
