package com.company.klinikTani.module.authentication.presenter.controller;

import com.company.klinikTani.module.authentication.service.UserService;
import com.company.klinikTani.module.authentication.model.UserDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/profiles")
    public ResponseEntity<Object> getUserProfile() {
        UserDetailsDto userDetailsDto = userService.getUserDetails();
        return new ResponseEntity<>(userDetailsDto, HttpStatus.OK);
    }
}
