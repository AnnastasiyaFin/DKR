package com.example.dkrproject;

import com.example.dkrproject.dto.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DkrProjectApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void testGetUsers() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/api/v1/user?pageNumber=0&pageSize=100",
                HttpMethod.GET, entity, String.class);

        assertNotNull(response.getBody());

        log.info(gson.toJson(JsonParser.parseString(response.getBody())));
    }

    @Test
    public void testGetQueryTypeById() {
        long id = 10106;
        ResponseEntity<String> userResponse = restTemplate.getForEntity(getRootUrl() + "/api/v1/user/" + id, String.class);
        assertNotNull(userResponse);
        if (userResponse.getBody() != null) {
            log.info("\n" + gson.toJson(JsonParser.parseString(userResponse.getBody())));
        }
    }

    @Test
    public void testCreateUser() {
        UserDTO userRequest = createNewUser();
        UserDTO userDTO = restTemplate.postForObject(getRootUrl() + "/api/v1/user", userRequest, UserDTO.class);
        assertNotNull(userDTO);
        log.info(gson.toJson(userDTO));
    }

    @Test
    public void testUpdateUser() {
        int id = 10127;
        UserDTO user = restTemplate.getForObject(getRootUrl() + "/api/v1/user/" + id, UserDTO.class);
        user.setName("Иван Иванов");

        restTemplate.put(getRootUrl() + "/api/v1/user/" + id, user);

        UserDTO updatedUserResponse = restTemplate.getForObject(getRootUrl() + "/api/v1/user/"
                + id, UserDTO.class);
        assertNotNull(updatedUserResponse);
        log.info(gson.toJson(updatedUserResponse));
    }

    @Test
    public void testDeleteEmployee() {
        UserDTO userRequest = createNewUser();
        UserDTO userDTO = restTemplate.postForObject(getRootUrl() + "/api/v1/user", userRequest, UserDTO.class);

        assertNotNull(userDTO);

        restTemplate.delete(getRootUrl() + "/api/v1/user/" + userDTO.getId());

        ResponseEntity<String> userResponse = restTemplate.getForEntity(getRootUrl() + "/api/v1/user/" + userDTO.getId(), String.class);
        assertEquals(userResponse.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testFilterByName() {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        Map<String, String> params = Collections.singletonMap("name", "Альвиан Гурьев");

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/api/v1/user/filterByName?name={name}",
                HttpMethod.GET, entity, String.class, params);

        assertNotNull(response.getBody());

        log.info(gson.toJson(JsonParser.parseString(response.getBody())));
    }

    @Test
    public void testFilterByNameKeyword() {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        Map<String, String> params = Collections.singletonMap("name", "Иван");

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/api/v1/user/filterByNameKeyword?name={name}",
                HttpMethod.GET, entity, String.class, params);

        assertNotNull(response.getBody());

        log.info(gson.toJson(JsonParser.parseString(response.getBody())));
    }

    @Test
    public void testFilterByDepartment() throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        Map<String, String> params = Collections.singletonMap("department", "Факультет прикладной математики и информатики");

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/api/v1/user/filterByDepartment?department={department}",
                HttpMethod.GET, entity, String.class, params);

        assertNotNull(response.getBody());

        log.info(gson.toJson(JsonParser.parseString(response.getBody())));
    }

    private UserDTO createNewUser() {
        UserDTO userRequest = new UserDTO();
        userRequest.setName("Олег Попов");
        userRequest.setLocation("Брест");
        userRequest.setPhone("+375295785785");
        userRequest.setBirthDate("1980-02-10");
        userRequest.setEmail("qwerty@mail.ru");
        userRequest.setDepartment("Факультет журналистики");

        return userRequest;
    }
}
