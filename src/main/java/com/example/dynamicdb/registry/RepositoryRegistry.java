package com.example.dynamicdb.registry;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

// Purpose: Given an Entity Class → return its JpaRepository

@Component
@RequiredArgsConstructor
public class RepositoryRegistry {

    private final ApplicationContext applicationContext;

    public JpaRepository<?, ?> getRepository(Class<?> entityClass) {

        String repositoryBeanName =
                Character.toLowerCase(entityClass.getSimpleName().charAt(0))
                        + entityClass.getSimpleName().substring(1)
                        + "Repository";

        return (JpaRepository<?, ?>) applicationContext.getBean(repositoryBeanName);
    }
}
