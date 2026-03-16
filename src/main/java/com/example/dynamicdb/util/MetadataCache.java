package com.example.dynamicdb.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetadataCache {

    private static final Map<Class<?>, List<FieldMetadata>> CACHE = new HashMap<>();

    public static List<FieldMetadata> get(Class<?> clazz) {
        return CACHE.get(clazz);
    }

    public static void put(Class<?> clazz, List<FieldMetadata> metadata) {
        CACHE.put(clazz, metadata);
    }

}