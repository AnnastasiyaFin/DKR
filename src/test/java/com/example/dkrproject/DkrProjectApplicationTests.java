package com.example.dkrproject;

import com.example.dkrproject.controller.UserController;
import com.example.dkrproject.dto.OrderDTO;
import com.example.dkrproject.exception.ResourceNotFoundException;
import com.example.dkrproject.repository.UserRepository;
import com.example.dkrproject.service.AuthorService;
import com.example.dkrproject.service.BookService;
import com.example.dkrproject.service.DepartmentService;
import com.example.dkrproject.service.OrderService;
import com.example.dkrproject.service.PublisherService;
import com.example.dkrproject.service.UserService;
import com.example.dkrproject.transformer.UserTransformer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DkrProjectApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DkrProjectApplicationTests {

    @Autowired
    AuthorService authorService;
    @Autowired
    BookService bookService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    OrderService orderService;
    @Autowired
    PublisherService publisherService;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserController userController;
    @Autowired
    UserTransformer userTransformer;
    @Autowired
    CacheManager cacheManager;

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    void contextLoads() {
        Assertions.assertNotNull(authorService);
        Assertions.assertNotNull(bookService);
        Assertions.assertNotNull(departmentService);
        Assertions.assertNotNull(orderService);
        Assertions.assertNotNull(publisherService);
        Assertions.assertNotNull(userService);

        Assertions.assertNotNull(userRepository);
        Assertions.assertNotNull(userController);
        Assertions.assertNotNull(userTransformer);
        Assertions.assertNotNull(cacheManager);
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void getBookList() {
        final StopWatch sw = new StopWatch("Execution time");
        sw.start();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/api/v1/book", HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
        log.info(gson.toJson(JsonParser.parseString(response.getBody())));
        sw.stop();
        System.out.println(sw.getTotalTimeMillis() + " millis");
    }

    @Test
    void getBookById() {
        final StopWatch sw = new StopWatch("Execution time");
        sw.start();
        final long bookId = 2;
        ResponseEntity<String> response = restTemplate.getForEntity(getRootUrl() + "/api/v1/book/{id}", String.class, bookId);
        assertNotNull(response.getBody());
        System.out.println("\n" + gson.toJson(JsonParser.parseString(response.getBody())));
        sw.stop();
        System.out.println("Execution time: "+ sw.getTotalTimeMillis() + " millis");
    }

    @Test
    void getBooksByAuthor() {
        final StopWatch sw = new StopWatch("Execution time");
        sw.start();
        final String authorName = "Полина Белова";
        ResponseEntity<String> response = restTemplate.getForEntity(getRootUrl() + "/api/v1/book/filterByAuthor?author={author}", String.class, authorName);
        assertNotNull(response.getBody());
        System.out.println("\n" + gson.toJson(JsonParser.parseString(response.getBody())));
        sw.stop();
        System.out.println("Execution time: "+ sw.getTotalTimeMillis() + " millis");
    }

    @Test
    void getBooksByCategory() {
        final StopWatch sw = new StopWatch("Execution time");
        sw.start();
        final String category = "рассказ";
        ResponseEntity<String> response = restTemplate.getForEntity(getRootUrl() + "/api/v1/book/filterByCategory?category={category}", String.class, category);
        assertNotNull(response.getBody());
        System.out.println("\n" + gson.toJson(JsonParser.parseString(response.getBody())));
        sw.stop();
        System.out.println("Execution time: "+ sw.getTotalTimeMillis() + " millis");
    }


    @Test
    void getBooksByCategoryGroupStreamApi() {
        final StopWatch sw = new StopWatch("Execution time");
        sw.start();
        final String category = "рассказ";
        ResponseEntity<String> response = restTemplate.getForEntity(getRootUrl() + "/api/v1/book/filterByCategoryStreamApi?category={category}", String.class, category);
        assertNotNull(response.getBody());
        System.out.println("\n" + gson.toJson(JsonParser.parseString(response.getBody())));
        sw.stop();
        System.out.println("Execution time: "+ sw.getTotalTimeMillis() + " millis");
    }

    @Test
    void getBooksByUser() {
        final StopWatch sw = new StopWatch("Execution time");
        sw.start();
        final String user = "Агата Владимировa";
        ResponseEntity<String> response = restTemplate.getForEntity(getRootUrl() + "/api/v1/book/orderedBooksByUser?user={user}", String.class, user);
        assertNotNull(response.getBody());
        System.out.println("\n" + gson.toJson(JsonParser.parseString(response.getBody())));
        sw.stop();
        System.out.println("Execution time: "+ sw.getTotalTimeMillis() + " millis");
    }

    @Test
    void getUserBooks() {
        final StopWatch sw = new StopWatch("Execution time");
        sw.start();
        final String user = "Агата Владимировa";
        ResponseEntity<String> response = restTemplate.getForEntity(getRootUrl() + "/api/v1/user/usersBooks?userName={user}", String.class, user);
        assertNotNull(response.getBody());
        System.out.println("\n" + gson.toJson(JsonParser.parseString(response.getBody())));
        sw.stop();
        System.out.println("Execution time: "+ sw.getTotalTimeMillis() + " millis");
    }

    @Test
    void getUserBooksByReaderCard() {
        final Long readerCard = 72L;
        ResponseEntity<String> response = restTemplate.getForEntity(getRootUrl() + "/api/v1/user/usersBooksByReaderCard?readerCard={readerCard}", String.class, readerCard);
        assertNotNull(response.getBody());
        System.out.println("\n" + gson.toJson(JsonParser.parseString(response.getBody())));
    }


    @Test
    void testOrders() throws ResourceNotFoundException {
        OrderDTO newOrder = createNewOrder();
        //данные о бронируемой книге
        ResponseEntity<String> response = restTemplate.getForEntity(getRootUrl() + "/api/v1/book/{id}", String.class, newOrder.getBookId());
        System.out.println("Бронируемая книга");
        System.out.println(gson.toJson(JsonParser.parseString(response.getBody())));

        //офорляем бронь книги
        OrderDTO orderCreated = restTemplate.postForObject(getRootUrl() + "/api/v1/order", newOrder, OrderDTO.class);
        assertNotNull(orderCreated);
        System.out.println("Бронь книги");
        System.out.println(gson.toJson(orderCreated));

        //данные о бронируемой книге,  количество уменьшилось на 1
        response = restTemplate.getForEntity(getRootUrl() + "/api/v1/book/{id}", String.class, newOrder.getBookId());
        System.out.println("Количество книг, из которых бронировали, уменьшилось на 1");
        System.out.println(gson.toJson(JsonParser.parseString(response.getBody())));

        Map<String, String> params = new HashMap<>();
        params.put("id", orderCreated.getId().toString());
        params.put("date", "2023-10-01");

        restTemplate.put(getRootUrl() + "/api/v1/order/extend?id={id}&date={date}", OrderDTO.class, params);
        ResponseEntity<OrderDTO> userDTOExtened = restTemplate.getForEntity(getRootUrl() + "/api/v1/order/{id}", OrderDTO.class, orderCreated.getId());
        assertNotNull(userDTOExtened);
        System.out.println("Продление бронирования книги, дата изменилась");
        System.out.println(gson.toJson(userDTOExtened.getBody()));

        restTemplate.put(getRootUrl() + "/api/v1/order/cancel/{id}", OrderDTO.class, params);
        ResponseEntity<OrderDTO> userDTOCanceled = restTemplate.getForEntity(getRootUrl() + "/api/v1/order/{id}", OrderDTO.class, orderCreated.getId());
        assertNotNull(userDTOCanceled);
        System.out.println("Возврат книги, проставление признака возврата книги в объекте Order");
        System.out.println(gson.toJson(userDTOCanceled.getBody()));

        //книгу вернули, данные о бронируемой книге, количество должно увеличиться на 1
        response = restTemplate.getForEntity(getRootUrl() + "/api/v1/book/{id}", String.class, newOrder.getBookId());
        System.out.println("Количество книг, из которых бронировали, увеличилось на 1");
        System.out.println(gson.toJson(JsonParser.parseString(response.getBody())));
    }

    private OrderDTO createNewOrder() throws ResourceNotFoundException {
        final long bookId = bookService.getRandom();
        final long readerCardId = userService.getRandom();
        return OrderDTO.builder().bookId(bookId).readerCardId(readerCardId).dateToReturn("2023-08-10").build();
    }


}
