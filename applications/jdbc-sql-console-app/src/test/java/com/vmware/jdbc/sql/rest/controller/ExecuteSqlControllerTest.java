package com.vmware.jdbc.sql.rest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExecuteSqlControllerTest {

    private ExecuteSqlController subject;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        subject = new ExecuteSqlController(jdbcTemplate);
    }

    @DisplayName("Given select when execute then return query list map results")
    @Test
    void query() {
        List<Map<String, Object>> expected = List.of(Map.of("name","Test"));
        when(jdbcTemplate.queryForList(anyString())).thenReturn(expected);

        String query = "select * from somewhere";
        var actual = subject.execute(query);


        assertEquals(expected, actual);
    }

    @DisplayName("Given delete when execute then update count")
    @Test
    void delete() {
        int count  = 2;
        List<Map<String, Object>> expected = List.of(Map.of("update",count));
        when(jdbcTemplate.update(anyString())).thenReturn(count);

        String query = "delete from somewhere";
        var actual = subject.execute(query);

        assertEquals(expected, actual);
    }

    @DisplayName("Given delete with spaces when execute then update count")
    @Test
    void deleteSpaces() {
        int count  = 2;
        List<Map<String, Object>> expected = List.of(Map.of("update",count));
        when(jdbcTemplate.update(anyString())).thenReturn(count);

        String query = " Delete from somewhere";
        var actual = subject.execute(query);

        assertEquals(expected, actual);
    }


    @DisplayName("Given update when execute then return query update count")
    @Test
    void update() {
        int count  = 2;
        List<Map<String, Object>> expected = List.of(Map.of("update",count));
        when(jdbcTemplate.update(anyString())).thenReturn(count);

        String query = "update from somewhere";
        var actual = subject.execute(query);

        assertEquals(expected, actual);
    }

    @DisplayName("Given update with spaces when execute then update count")
    @Test
    void updateSpaces() {
        int count  = 2;
        List<Map<String, Object>> expected = List.of(Map.of("update",count));
        when(jdbcTemplate.update(anyString())).thenReturn(count);

        String query = " Update from somewhere";
        var actual = subject.execute(query);

        assertEquals(expected, actual);
    }
}