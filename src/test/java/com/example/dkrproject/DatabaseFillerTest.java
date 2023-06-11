package com.example.dkrproject;

import com.example.dkrproject.filler.DatabaseFiller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatNoException;

@SpringBootTest
class DatabaseFillerTest {

    @Autowired
    private DatabaseFiller databaseFiller;

    @Test
    void fillDatabase() {
        final int usersCount = 0;
        final int publishersCount = 0;
        final int booksCount = 0;
        final int ordersCount = 80_000;

        assertThatNoException()
                .isThrownBy(() -> databaseFiller.fillDatabase(usersCount, publishersCount, booksCount, ordersCount));
    }
}