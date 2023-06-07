package com.example.dkrproject.transformer;

import com.example.dkrproject.dto.BookDTO;
import com.example.dkrproject.dto.UserBooksDTO;
import com.example.dkrproject.dto.UserDTO;
import com.example.dkrproject.model.Book;
import com.example.dkrproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserTransformer {

    @Autowired
    BookTransformer bookTransformer;

    public UserDTO transformUserToDTO(final User user) {
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

    public User transformUserToDTO(final UserDTO userDTO) {
        return User.builder()
                .name(userDTO.getName())
                .birthDate(LocalDate.parse(userDTO.getBirthDate()))
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .build();
    }

    public UserBooksDTO transformUserToUserBooksDTO(User user) {
        UserBooksDTO userBooksDTO = UserBooksDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .birthDate(user.getBirthDate().format(DateTimeFormatter.ISO_DATE))
                .email(user.getEmail())
                .phone(user.getPhone())
                .location(user.getLocation().getName())
                .build();
        if (user.getDepartment() != null) {
            userBooksDTO.setDepartment(user.getDepartment().getName());
        }
        if (user.getOrders() != null) {
            List<BookDTO> books = new ArrayList<>();
            user.getOrders().forEach(o -> books.add(bookTransformer.transformBookToResp(o.getBook())));
            userBooksDTO.setBooks(books);
        }
        return userBooksDTO;
    }
}
