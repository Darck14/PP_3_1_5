package com.example.resttemplate.service;

import com.example.resttemplate.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public interface UserService {

    ResponseEntity<String> getAllUsers(RestTemplate restTemplate);

    String saveUser(RestTemplate restTemplate, User user);

    String updateUser(RestTemplate restTemplate, User user);

    String deleteUserById(RestTemplate restTemplate, Long id);

    String getSessionId(ResponseEntity<String> response);

    String showCode();

}
