package com.example.dynamicdb.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ViewQueryRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> fetchDropdown(String viewName) {

        if (!viewName.matches("[a-zA-Z0-9_]+")) {
            throw new IllegalArgumentException("Invalid view name: " + viewName);
        }

        String sql = "SELECT id, label FROM " + viewName;

        return jdbcTemplate.queryForList(sql);
    }
}