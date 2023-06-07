package com.example.dkrproject.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthorDTO {

    private Long id;
    @NotEmpty(message = "Author name should not be null")
    private String name;
    @NotEmpty(message = "Date of birth should not be null")
    private String dateOfBirth;
}
