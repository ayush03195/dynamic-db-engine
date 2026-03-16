package com.example.dynamicdb.config;

import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {

        // Later we will integrate with Spring Security
        // For now return static user
        return Optional.of("SYSTEM");
    }
}
