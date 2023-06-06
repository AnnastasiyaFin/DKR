package com.example.dkrproject.transformer;

import com.example.dkrproject.dto.UserDTO;
import com.example.dkrproject.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class UserTransformer {

    public UserDTO transformDTOToUser(final User user) {
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .birthDate(user.getBirthDate().format(DateTimeFormatter.ISO_DATE))
                .email(user.getEmail())
                .phone(user.getPhone())
                .location(user.getLocation().getName())
                .build();
        if (user.getDepartment() != null) {
            userDTO.setDepartment(user.getDepartment().getName());
        }
        return userDTO;
    }

    public User transformDTOToUser(final UserDTO userDTO) {
        return User.builder()
                .name(userDTO.getName())
                .birthDate(LocalDate.parse(userDTO.getBirthDate()))
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .build();
    }
}
