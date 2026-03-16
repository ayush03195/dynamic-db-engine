package com.example.dynamicdb.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Field;

@Getter
@AllArgsConstructor
public class FieldMetadata {

    private Field field;
    private boolean dropdown;
    private String viewName;

}