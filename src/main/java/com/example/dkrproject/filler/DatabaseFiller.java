package com.example.dkrproject.filler;

import com.example.dkrproject.dto.AuthorDTO;
import com.example.dkrproject.dto.BookDTO;
import com.example.dkrproject.dto.OrderDTO;
import com.example.dkrproject.dto.PublisherDTO;
import com.example.dkrproject.dto.UserDTO;
import com.example.dkrproject.exception.ResourceNotFoundException;
import com.example.dkrproject.service.AuthorService;
import com.example.dkrproject.service.BookService;
import com.example.dkrproject.service.DepartmentService;
import com.example.dkrproject.service.OrderService;
import com.example.dkrproject.service.PublisherService;
import com.example.dkrproject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseFiller {

    private final RandomUserCreator randomUserCreator;
    private final AuthorCreator authorCreator;
    private final DepartmentCreator departmentCreator;
    private final RandomBookCreator randomBookCreator;
    private final RandomPublisherCreator randomPublisherCreator;
    private final UserService userService;
    private final DepartmentService departmentService;
    private final PublisherService publisherService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final OrderService orderService;

    public void fillDatabase(final int usersCount, final int publishersCount, final int booksCount,
                             final int ordersCount) {
        fillDepartments();
        fillAuthors();
        createUsers(usersCount);
        createPublishers(publishersCount);
        createBooks(booksCount);
        createOrders(ordersCount);
    }

    private void fillDepartments() {
        final List<String> depList = departmentCreator.getDepartmentsList();
        depList.forEach(departmentService::saveDepartment);
    }

    private void fillAuthors() {
        final List<AuthorDTO> authorDTOList = authorCreator.getAuthorsList();
        authorDTOList.forEach(authorService::save);
    }

    private void createBooks(int bookAmount) {
        int createdBooks = 0;
        while (createdBooks < bookAmount) {
            final BookDTO book = randomBookCreator.randomBook();
            try {
                bookService.save(book);
                createdBooks++;
            } catch (ResourceNotFoundException e) {
                log.debug(e.getMessage());
            }
        }
    }

    private void createPublishers(int publishersAmount) {
        int createdPublishers = 0;
        while (createdPublishers < publishersAmount) {
            final PublisherDTO publisherDTO = randomPublisherCreator.randomPublisher();
            publisherService.save(publisherDTO);
            createdPublishers++;
        }
    }

    private void createUsers(final int usersAmount) {
        int createdUsers = 0;
        while (createdUsers < usersAmount) {
            final UserDTO userDTO = randomUserCreator.randomRequest();
            try {
                userService.saveUser(userDTO);
                createdUsers++;
            } catch (ResourceNotFoundException e) {
                log.debug(e.getMessage());
            }
        }
    }

    private void createOrders(final int ordersAmount) {
        int createdOrders = 0;
        while (createdOrders < ordersAmount) {
            final long bookId;
            final long userId;

            try {
                bookId = bookService.getRandom();
                userId = userService.getRandom();
            } catch (ResourceNotFoundException e) {
                log.debug(e.getMessage());
                continue;
            }

            try {
                OrderDTO order = OrderDTO.builder()
                        .bookId(bookId)
                        .userId(userId)
                        .orderDate(getDateStart())
                        .dateToReturn(getDateEnd())
                        .isReturned(RandomUtils.nextBoolean())
                        .build();
                orderService.fillOrdersDB(order);
                createdOrders++;
            } catch (ResourceNotFoundException e) {
                log.debug(e.getMessage());
            }
        }
    }

    private String getDateEnd() {
        return LocalDate.now().minusDays(RandomUtils.nextInt(0, 20)).format(DateTimeFormatter.ISO_DATE);
    }

    private String getDateStart() {
        return LocalDate.now().plusDays(RandomUtils.nextInt(0, 20)).format(DateTimeFormatter.ISO_DATE);
    }
}