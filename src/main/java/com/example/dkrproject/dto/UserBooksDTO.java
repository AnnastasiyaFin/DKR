package com.example.dkrproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBooksDTO {

    private Long id;
    private String name;
    private String birthDate;
    private String location;
    private String email;
    private String phone;
    private String department;
    private List<BookDTO> books;
}
