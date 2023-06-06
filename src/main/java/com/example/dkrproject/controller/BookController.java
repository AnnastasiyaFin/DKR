package com.example.dkrproject.controller;

import com.example.dkrproject.dto.BookDTO;
import com.example.dkrproject.exception.ResourceNotFoundException;
import com.example.dkrproject.service.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/book")
@Tag(name = "Книги", description = "Получение данных о книгах")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return new ResponseEntity<>(bookService.findAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(bookService.findBookDTOById(id), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public BookDTO addBook(@Valid @RequestBody BookDTO bookRequest) throws ResourceNotFoundException {
        return bookService.save(bookRequest);
    }

    @PutMapping("/add")
    public ResponseEntity<BookDTO> addBookAmount(@RequestParam long id,
                                                      @RequestParam @Min(1) int amount) throws ResourceNotFoundException {
        BookDTO bookUpdated = bookService.updateAmount(id, "add", amount);
        return ResponseEntity.ok().body(bookUpdated);
    }

    @PutMapping("/remove")
    public ResponseEntity<BookDTO> removeBookAmount(@RequestParam long id,
                                                         @RequestParam @Min(1) int amount) throws ResourceNotFoundException {
        BookDTO bookUpdated = bookService.updateAmount(id, "remove", amount);
        return ResponseEntity.ok().body(bookUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/filterByAuthor")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@RequestParam String author) {
        List<BookDTO> books = bookService.getBooksByAuthor(author);
        return ResponseEntity.ok().body(books);
    }

    @GetMapping("/filterByCategory")
    public ResponseEntity<List<BookDTO>> getBooksByCategory(@RequestParam String category) {
        List<BookDTO> books = bookService.getBooksByCategory(category);
        return ResponseEntity.ok().body(books);
    }

    @GetMapping("/orderedBooksByUser")
    public ResponseEntity<List<BookDTO>> getBooksByUser(@RequestParam String user) {
        List<BookDTO> books = bookService.getBooksByUser(user);
        return ResponseEntity.ok().body(books);
    }
}
