package com.example.dkrproject.filler;

import com.example.dkrproject.dto.AuthorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class AuthorCreator {

    private final Vocabulary vocabulary;

    public List<AuthorDTO> getAuthorsList() {
        List<AuthorDTO> authors = new ArrayList<>();
        vocabulary.getFullAuthorNameList().forEach(a -> {
            authors.add(AuthorDTO.builder()
                    .name(a)
                    .dateOfBirth(generateBirthDate())
                    .build());
        });

        return authors;
    }

    private String generateBirthDate() {
        final long minDay = LocalDate.of(1700, 1, 1).toEpochDay();
        final long maxDay = LocalDate.of(2000, 12, 31).toEpochDay();
        final long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay).format(DateTimeFormatter.ISO_DATE);
    }

}