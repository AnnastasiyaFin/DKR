package com.example.dkrproject.filler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Component
public class Vocabulary {

    private final List<String> maleNames = loadVocabulary("male_names");
    private final List<String> femaleNames = loadVocabulary("female_names");
    private final List<String> surnames = loadVocabulary("surnames");
    private final List<String> mailServices = loadVocabulary("mail_services");
    private final List<String> cities = loadVocabulary("cities");
    private final List<String> departments = loadVocabulary("departments");
    private final List<String> authors = loadVocabulary("authors");
    private final List<String> bookTitles = loadVocabulary("book_titles");
    private final List<String> categories = loadVocabulary("categories");
    private final List<String> publishers = loadVocabulary("publishers");

    public String getName() {
        String userName;
        if (RandomUtils.nextBoolean()) {
            userName = String.format("%s %s", maleNames.get(RandomUtils.nextInt(0, maleNames.size())),
                    surnames.get(RandomUtils.nextInt(0, surnames.size())));
        } else {
            userName = String.format("%s %s", femaleNames.get(RandomUtils.nextInt(0, femaleNames.size())),
                    surnames.get(RandomUtils.nextInt(0, surnames.size())) + "a");
        }
        return userName;
    }

    public String getMailService() {
        return mailServices.get(RandomUtils.nextInt(0, mailServices.size()));
    }

    public String getCityToPublisher() {
        return cities.get(RandomUtils.nextInt(0, cities.size()));
    }

    public String getCityToUser() {
        return cities.get(RandomUtils.nextInt(0, 14));
    }

    public String getDepartment() {
        return departments.get(RandomUtils.nextInt(0, departments.size()));
    }

    public List<String> getFullAuthorNameList() {
        return authors;
    }

    public String getAuthorName() {
        return authors.get(RandomUtils.nextInt(0, authors.size()));
    }

    public List<String> getFullDepartmentsList() {
        return departments;
    }

    public String getBookTitle() {
        return bookTitles.get(RandomUtils.nextInt(0, bookTitles.size()));
    }

    public String getCategory() {
        return categories.get(RandomUtils.nextInt(0, categories.size()));
    }

    public String getPublisher() {
        return publishers.get(RandomUtils.nextInt(0, publishers.size()));
    }

    private List<String> loadVocabulary(final String fileName) {
        final String path = "src/main/resources/vocabulary/";
        final String extension = ".txt";
        final File file = new File(path + fileName + extension);
        List<String> vocabulary;
        try {
            vocabulary = FileUtils.readLines(file, StandardCharsets.UTF_8);
            log.info(String.format("Vocabulary %1$s has been loaded! %1$s size is %2$s.", fileName, vocabulary.size()));
            return vocabulary;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}