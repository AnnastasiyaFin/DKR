package com.example.dkrproject.transformer;

import com.example.dkrproject.dto.BookDTO;
import com.example.dkrproject.model.Author;
import com.example.dkrproject.model.Book;
import com.example.dkrproject.model.Category;
import com.example.dkrproject.model.Publisher;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookTransformer {

    public Book transformBookRequest(final BookDTO bookRequest) {
        return Book.builder()
                .id(bookRequest.getId())
                .title(bookRequest.getTitle())
                .yearPublished(bookRequest.getYearPublished())
                .amountAvailable(bookRequest.getAmountAvailable())
                .category(new Category(bookRequest.getCategory()))
                .publisher(new Publisher(bookRequest.getPublisher()))
                .build();
    }

    public BookDTO transformBookToResp(final Book book) {
        Set<String> authors = book.getAuthors().stream().map(Author::getName).collect(Collectors.toSet());
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .yearPublished(book.getYearPublished())
                .amountAvailable(book.getAmountAvailable())
                .category(book.getCategory().getName())
                .publisher(book.getPublisher().getName())
                .authors(authors)
                .build();
    }
}
