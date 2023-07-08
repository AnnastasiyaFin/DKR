package com.example.dkrproject.repository;

import com.example.dkrproject.model.Category;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Cacheable("category")
    Category findByName(String category);
}
