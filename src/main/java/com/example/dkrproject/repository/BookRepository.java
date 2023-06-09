package com.example.dkrproject.repository;

import com.example.dkrproject.model.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(attributePaths = {"category", "publisher", "authors"})
    Optional<Book> findById(long id);

    @Query(value = "SELECT * FROM book ORDER BY random() LIMIT 1", nativeQuery = true)
    Optional<Book> findRandom();

    @Query(value = "select b.* FROM Book b INNER JOIN book_author ba ON b.id = ba.book_id " +
            "INNER JOIN Author a ON a.id = ba.author_id WHERE a.name LIKE %:author%",
            nativeQuery = true)
    List<Book> getBooksByAuthor(String author);

    @Query(value = "select b.* FROM Book b INNER JOIN Category c ON c.id = b.category_id WHERE c.name = :category",
            nativeQuery = true)
    List<Book> findByCategory(String category);

    @Query(value = "select b.* FROM Book b INNER JOIN Orders o on b.id = o.book_id " +
            "INNER JOIN Reader_Card rc ON rc.id = o.reader_card_id " +
            "INNER JOIN Users u ON u.reader_card_id = rc.id WHERE u.name = :user",
            nativeQuery = true)
    List<Book> findByUser(String user);
}
