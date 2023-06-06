package com.example.dkrproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublisherDTO {

    private Long id;
    @NotEmpty(message = "Publisher name should not be null")
    private String name;
    @NotBlank(message = "Phone number should not be null")
    @Pattern(regexp = "^\\+375\\d{9}$", message = "Enter correct phone number (+375XXXXXXX)")
    private String phone;
    @NotBlank(message = "Email should not be null")
    @Email(message = "Wrong email")
    private String email;
    @NotEmpty(message = "Location should not be null")
    private String location;
    private Set<String> books = new HashSet<>();

}
