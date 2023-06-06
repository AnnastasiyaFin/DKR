package com.example.dkrproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthorDTO {

    private Long id;
    private String name;
    private String dateOfBirth;
}
