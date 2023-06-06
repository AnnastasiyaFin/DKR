package com.example.dkrproject.controller;

import com.example.dkrproject.exception.ResourceNotFoundException;
import com.example.dkrproject.model.Author;
import com.example.dkrproject.dto.AuthorDTO;
import com.example.dkrproject.service.AuthorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/author")
@Tag(name="Авторы книг", description="Получение данных об авторах")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        return new ResponseEntity<>(authorService.findAllAuthors(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Author> addAuthor(@RequestBody AuthorDTO authorRequest) {
        return new ResponseEntity<>(authorService.save(authorRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable("id") long id,
                                               @RequestBody AuthorDTO authorRequest) throws ResourceNotFoundException {
        Author authorUpdated = authorService.updateAuthor(id, authorRequest);
        return ResponseEntity.ok().body(authorUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAuthor(@PathVariable("id") long id) {
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
