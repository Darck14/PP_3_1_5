package com.example.resttemplate.service;

import com.example.resttemplate.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final String URL = "http://94.198.50.185:7081/api/users";
    private String sessionId;
    private User userSave = new User(3L, "James", "Brown", (byte) 25);


    private final RestTemplate restTemplate;

    @Autowired
    public UserServiceImp(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Override
    public ResponseEntity<String> getAllUsers(RestTemplate restTemplate) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                URL,
                HttpMethod.GET,
                entity,
                String.class
        );

        System.out.println("Users: " + response.getBody());
        return response;
    }

    @Override
    public String saveUser(RestTemplate restTemplate, User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.COOKIE, sessionId);

        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                entity,
                String.class
        );

        System.out.println("Save response: " + response.getBody());
        return response.getBody();
    }

    @Override
    public String updateUser(RestTemplate restTemplate, User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.COOKIE, sessionId);

        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                URL,
                HttpMethod.PUT,
                entity,
                String.class
        );

        System.out.println("Update response: " + response.getBody());
        return response.getBody();
    }

    @Override
    public String deleteUserById(RestTemplate restTemplate, Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, sessionId);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                URL + "/" + id,
                HttpMethod.DELETE,
                entity,
                String.class
        );

        System.out.println("Delete response: " + response.getBody());
        return response.getBody();
    }

    @Override
    public String getSessionId(ResponseEntity<String> response) {
        List<String> cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);
        if (cookies != null && !cookies.isEmpty()) {
            return cookies.get(0).split(";")[0];
        }
        throw new RuntimeException("Session ID not found!");
    }

    @Override
    public String showCode() {
        ResponseEntity<String> response = getAllUsers(restTemplate);
        sessionId = getSessionId(response);
        System.out.println(sessionId);
        String part1 = saveUser(restTemplate, userSave);

        userSave.setName("Thomas");
        userSave.setLastName("Shelby");
        String part2 = updateUser(restTemplate, userSave);

        String part3 = deleteUserById(restTemplate, 3L);

        return ("Result code: " + part1 + part2 + part3);
    }
}
