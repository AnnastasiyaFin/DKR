package com.example.dkrproject.filler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DepartmentCreator {
    private final Vocabulary vocabulary;

    public List<String> getDepartmentsList() {
        return vocabulary.getFullDepartmentsList();
    }

}
