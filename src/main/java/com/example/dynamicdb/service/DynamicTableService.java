package com.example.dynamicdb.service;

import com.example.dynamicdb.dto.DynamicTableResponse;

import java.util.List;
import java.util.Map;

public interface DynamicTableService {

    DynamicTableResponse getTableData(String tableName, int page, int size);

}