package com.example.dkrproject.transformer;

import com.example.dkrproject.dto.PublisherDTO;
import com.example.dkrproject.model.Book;
import com.example.dkrproject.model.Publisher;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PublisherTransformer {

    public PublisherDTO transformPublisherToResp(final Publisher publisher) {
        Set<String> books = publisher.getBooks().stream().map(Book::getTitle).collect(Collectors.toSet());
        return PublisherDTO.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .email(publisher.getEmail())
                .phone(publisher.getPhone())
                .location(publisher.getLocation().getName())
                .books(books)
                .build();
    }

    public PublisherDTO transformPublisherWithoutBooks(final Publisher publisher) {
        return PublisherDTO.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .email(publisher.getEmail())
                .phone(publisher.getPhone())
                .location(publisher.getLocation().getName())
                .build();
    }
}
