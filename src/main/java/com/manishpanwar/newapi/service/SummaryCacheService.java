package com.manishpanwar.newapi.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SummaryCacheService {
    private final Map<String, String> cache = new ConcurrentHashMap<>();

    public String get(String id) {
        return cache.get(id);
    }

    public void put(String id, String summary) {
        cache.put(id, summary);
    }

    public String computeIfAbsent(String id, java.util.function.Function<String, String> mappingFunction) {
        return cache.computeIfAbsent(id, mappingFunction);
    }
}
