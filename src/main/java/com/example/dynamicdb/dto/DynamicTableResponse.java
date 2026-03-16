package com.example.dynamicdb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class DynamicTableResponse {

    private int page;
    private int size;
    private long totalRecords;
    private List<Map<String, Object>> data;
}