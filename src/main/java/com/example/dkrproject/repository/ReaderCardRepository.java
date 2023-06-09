package com.example.dkrproject.repository;

import com.example.dkrproject.model.ReaderCard;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReaderCardRepository extends JpaRepository<ReaderCard, Long> {

    @EntityGraph(attributePaths = {"user", "readercard"})
    Optional<Object> findReaderCardByUserId(long userId);
}
