package com.example.resttemplate.controller;

import com.example.resttemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/code")
public class RequestController {

    private final UserService userService;

    @Autowired
    public RequestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showCode() {
        return userService.showCode();
    }
}
