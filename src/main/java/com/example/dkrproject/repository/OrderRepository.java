package com.example.dkrproject.repository;

import com.example.dkrproject.model.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

//    @EntityGraph(attributePaths = {"user", "book"})
//    List<Order> findAll();
}
