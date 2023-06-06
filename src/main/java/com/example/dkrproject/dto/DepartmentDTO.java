package com.example.dkrproject.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentDTO {

    private Long id;
    @NotEmpty(message = "Department name should not be null")
    private String name;
}
