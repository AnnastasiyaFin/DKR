package com.example.dkrproject.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Builder
@Getter
@Setter
public class BookDTO {

    private Long id;
    @NotEmpty(message = "Title should not be null")
    private String title;
    @NotEmpty(message = "Year published should not be null")
    private String yearPublished;
    @Min(value = 1, message = "Minimum amount is 1")
    @Max(value = 100, message = "Maximum amount is 100")
    private int amountAvailable;

    @NotBlank(message = "Authors should not be null")
    private Set<String> authors;

    @NotBlank(message = "Category should not be null")
    private String category;

    @NotBlank(message = "Publisher should not be null")
    private String publisher;
}
