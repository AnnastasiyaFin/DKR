package com.example.dkrproject.filler;

import com.example.dkrproject.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RandomBookCreator {

    private final Vocabulary vocabulary;

    public BookDTO randomBook() {
        final String category = vocabulary.getCategory();
        final String publisher = vocabulary.getPublisher();

        return BookDTO.builder()
                .title(getTitle())
                .publisher(publisher)
                .category(category)
                .yearPublished(generateYear())
                .authors(getAuthors())
                .amountAvailable(getAmount())
                .build();
    }

    private Set<String> getAuthors() {
        Set<String> authors = new HashSet<>();
        if (RandomUtils.nextBoolean()) {
            authors.add(vocabulary.getAuthorName());
        } else {
            authors.add(vocabulary.getAuthorName());
            authors.add(vocabulary.getAuthorName());
        }
        return authors;
    }

    private String getTitle() {
        return vocabulary.getBookTitle() + " " + RandomStringUtils.randomAlphabetic(3).toUpperCase();
    }

    private String generateYear() {
        return String.valueOf(RandomUtils.nextInt(1950, 2023));
    }

    private int getAmount() {
        return RandomUtils.nextInt(1, 20);
    }

}