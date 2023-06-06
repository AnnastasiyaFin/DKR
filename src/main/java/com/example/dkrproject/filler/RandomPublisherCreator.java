package com.example.dkrproject.filler;

import com.example.dkrproject.dto.PublisherDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class RandomPublisherCreator {

    private final Vocabulary vocabulary;

    public PublisherDTO randomPublisher() {
        final String publisher = vocabulary.getPublisher();
        final String location = vocabulary.getCityToPublisher();

        return PublisherDTO.builder()
                .name(publisher)
                .email(generateEmail())
                .phone(generatePhone())
                .location(location)
                .build();
    }

    private String generatePhone() {
        return "+375" + RandomStringUtils.randomNumeric(9);
    }

    private String generateEmail() {
        final StringBuilder sb = new StringBuilder();
        sb.append(RandomStringUtils.random(8, true, true).toLowerCase(Locale.ROOT));
        sb.append(vocabulary.getMailService());
        return sb.toString();
    }
}
