package com.example.dkrproject.repository;

import com.example.dkrproject.model.Department;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Cacheable("department")
    List<Department> findByName(String name);

}
