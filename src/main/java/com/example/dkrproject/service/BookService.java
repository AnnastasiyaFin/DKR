package com.example.dkrproject.service;

import com.example.dkrproject.dto.BookDTO;
import com.example.dkrproject.exception.ResourceNotFoundException;
import com.example.dkrproject.model.Author;
import com.example.dkrproject.model.Book;
import com.example.dkrproject.model.Category;
import com.example.dkrproject.model.Publisher;
import com.example.dkrproject.repository.BookRepository;
import com.example.dkrproject.repository.CategoryRepository;
import com.example.dkrproject.transformer.BookTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PublisherService publisherService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookTransformer bookTransformer;

    public List<BookDTO> findAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(bookTransformer::transformBookToResp).toList();
    }

    public Book findById(Long bookId) throws ResourceNotFoundException {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found for this id :: " + bookId));
    }

    public BookDTO findBookDTOById(Long bookId) throws ResourceNotFoundException {
        return bookTransformer.transformBookToResp(findById(bookId));
    }

    @Transactional(rollbackFor = Throwable.class)
    public BookDTO save(BookDTO bookRequest) throws ResourceNotFoundException {
        Category category = categoryRepository.findByName(bookRequest.getCategory());
        if (category == null) {
            category = categoryRepository.save(new Category(bookRequest.getCategory()));
        }
        Publisher publisher = publisherService.findPublisherByName(bookRequest.getPublisher());
        if (publisher == null) {
            throw new ResourceNotFoundException("Publisher not found :: " + bookRequest.getPublisher());
        }
        Book book = Book.builder()
                .title(bookRequest.getTitle())
                .yearPublished(bookRequest.getYearPublished())
                .amountAvailable(bookRequest.getAmountAvailable())
                .category(category)
                .publisher(publisher)
                .build();

        assignAuthorsToBook(book, bookRequest.getAuthors());

        book = bookRepository.save(book);
        return bookTransformer.transformBookToResp(book);
    }

    public BookDTO updateAmount(long id, String operation, int amount) throws ResourceNotFoundException {
        Book book = findById(id);
        switch (operation) {
            case "add" -> book.setAmountAvailable(book.getAmountAvailable() + amount);
            case "remove" -> book.setAmountAvailable(book.getAmountAvailable() - amount);
        }
        return bookTransformer.transformBookToResp(bookRepository.save(book));
    }

    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }

    private void assignAuthorsToBook(Book book, Set<String> authorNameList) throws ResourceNotFoundException {
        Set<Author> authorSet = new HashSet<>();
        for (String authorName : authorNameList) {
            Author author = authorService.getAuthorByName(authorName);
            if (author == null) {
                throw new ResourceNotFoundException("Author not found for this id :" + authorName);
            }
            authorSet.add(author);
        }
        book.setAuthors(authorSet);
    }

    public long getRandom() throws ResourceNotFoundException {
        Book book = bookRepository.findRandom()
                .orElseThrow(() -> new ResourceNotFoundException("No book found"));
        return book.getId();
    }

    public List<BookDTO> getBooksByAuthor(String author) {
        List<Book> list = bookRepository.getBooksByAuthor(author);
        return list.stream().map(bookTransformer::transformBookToResp).toList();
    }

    public List<BookDTO> getBooksByCategory(String category) {
        List<Book> list = bookRepository.findByCategory(category);
        return list.stream().map(bookTransformer::transformBookToResp).toList();
    }

    public List<BookDTO> getBooksByUser(String user) {
        List<Book> list = bookRepository.findByUser(user);
        return list.stream().map(bookTransformer::transformBookToResp).toList();
    }

    public List<BookDTO> getBooksByCategoryStreamApi(String category) {

        List<BookDTO> bookDTOs = findAllBooks();
        return bookDTOs.stream().filter(b -> category.equalsIgnoreCase(b.getCategory())).collect(Collectors.toList());
    }
}
