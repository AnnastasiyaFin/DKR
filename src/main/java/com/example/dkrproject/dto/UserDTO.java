package com.example.dkrproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    @NotBlank(message = "User name should not be null")
    private String name;
    @NotBlank(message = "Birth date should not be null")
    private String birthDate;
    @NotBlank(message = "Location should not be null")
    private String location;
    @NotBlank(message = "Email should not be null")
    @Email(message = "Wrong email")
    private String email;
    @NotBlank(message = "Phone number should not be null")
    @Pattern(regexp = "^\\+375\\d{9}$", message = "Enter correct phone number (+375XXXXXXX)")
    private String phone;
    private String department;
    private Long readerCardId;

    @Override
    public String toString() {
        return "id: " + id +
                "\nname: " + name +
                "\nbirtDate: " + birthDate +
                "\nemail: " + email +
                "\nphone: " + phone +
                "\nlocation: " + location +
                "\ndepartments: " + department +
                "\nreader card id: " + readerCardId;
    }

}
