package com.example.dkrproject.controller;

import com.example.dkrproject.dto.UserBooksDTO;
import com.example.dkrproject.dto.UserDTO;
import com.example.dkrproject.exception.ResourceNotFoundException;
import com.example.dkrproject.model.User;
import com.example.dkrproject.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@Tag(name = "Читатели", description = "Различные виды операций с таблицей \"Читатели\"")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<UserDTO> getAllUsers(@Min(0) int pageNumber, @Min(1) @Max(1000)  int pageSize) {
        return userService.getAllUsers(pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserDTO getUserById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        return userService.qetUserResponseById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void deleteUserById(@PathVariable("id") long id) {
         userService.deleteUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserDTO createUser(@Valid @RequestBody UserDTO userRequest) throws ResourceNotFoundException {
        return userService.saveUser(userRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") long id, @Valid @RequestBody UserDTO userRequest) throws ResourceNotFoundException {
        UserDTO userResponse = userService.updateUser(id, userRequest);
        return ResponseEntity.ok().body(userResponse);
    }

    @GetMapping("/filterByName")
    public ResponseEntity<List<UserDTO>> getUserByName(@RequestParam @NotBlank @Size(max = 30) String name) {
        List<UserDTO> users = userService.getUsersByName(name);
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/filterByNameKeyword")
    public ResponseEntity<List<UserDTO>> getUsersByNameKeyword(@RequestParam @NotBlank @Size(max = 30) String name) {
        List<UserDTO> users = userService.getUsersByNameContaining(name);
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/filterByDepartment")
    public ResponseEntity<List<UserDTO>> getUsersByDepartment(@RequestParam @NotBlank @Size(max = 50) String department) {
        List<UserDTO> users = userService.getUsersByDepartment(department);
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/usersBooks")
    public ResponseEntity<List<UserBooksDTO>> getUsersBooks(@RequestParam @NotBlank @Size(max = 30) String userName) {
        List<UserBooksDTO> usersBooks = userService.getUsersBooks(userName);
        return ResponseEntity.ok().body(usersBooks);
    }

    @GetMapping("/usersBooksByReaderCard")
    public ResponseEntity<List<UserBooksDTO>> getUsersBooksByReaderCard(@RequestParam @NotBlank @Min(1) Long readerCard) {
        List<UserBooksDTO> usersBooks = userService.getUsersBooksByReaderCard(readerCard);
        return ResponseEntity.ok().body(usersBooks);
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<Integer> deleteUserByName(@PathVariable("name") @NotBlank String name) {
        return new ResponseEntity<>(userService.deleteUserByName(name), HttpStatus.OK);
    }

}
