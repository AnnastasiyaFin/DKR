package com.example.dkrproject.repository;

import com.example.dkrproject.model.Publisher;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    @EntityGraph(attributePaths = {"books"})
    @Cacheable("publisher")
    List<Publisher> findByName(String name);

}
