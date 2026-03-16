package com.example.dynamicdb.controller;

import com.example.dynamicdb.dto.DynamicTableResponse;
import com.example.dynamicdb.service.DynamicTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tables")
@RequiredArgsConstructor
public class DynamicTableController {

    private final DynamicTableService dynamicTableService;

    @GetMapping("/{tableName}")
    public DynamicTableResponse getTableData(
            @PathVariable String tableName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        return dynamicTableService.getTableData(tableName, page, size);
    }
}