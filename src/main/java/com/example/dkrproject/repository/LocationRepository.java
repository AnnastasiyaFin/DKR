package com.example.dkrproject.repository;

import com.example.dkrproject.model.Location;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    @Cacheable("location")
    List<Location> findByName(String name);

}
