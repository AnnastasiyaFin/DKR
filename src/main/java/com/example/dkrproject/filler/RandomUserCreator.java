package com.example.dkrproject.filler;

import com.example.dkrproject.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class RandomUserCreator {

    private final Vocabulary vocabulary;

    public UserDTO randomRequest() {
        final String name = vocabulary.getName();
        final String location = vocabulary.getCityToUser();
        final String department = vocabulary.getDepartment();

        return UserDTO.builder()
                .name(name)
                .birthDate(generateBirthDate())
                .phone(generatePhone())
                .email(generateEmail())
                .location(location)
                .department(department)
                .build();
    }

    private String generateBirthDate() {
        final long minDay = LocalDate.of(1960, 1, 1).toEpochDay();
        final long maxDay = LocalDate.of(2002, 12, 31).toEpochDay();
        final long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay).format(DateTimeFormatter.ISO_DATE);
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