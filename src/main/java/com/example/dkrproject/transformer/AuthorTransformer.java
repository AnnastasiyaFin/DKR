package com.example.dkrproject.transformer;

import com.example.dkrproject.model.Author;
import com.example.dkrproject.dto.AuthorDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AuthorTransformer {

    public Author transformAuthorRequest(final AuthorDTO authorRequest) {
        return Author.builder()
                .name(authorRequest.getName())
                .dateOfBirth(LocalDate.parse(authorRequest.getDateOfBirth()))
                .build();
    }
}
