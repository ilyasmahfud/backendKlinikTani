package com.company.klinikTani.module.authentication.presenter.controller;

import com.company.klinikTani.module.authentication.entity.UserDetails;
import com.company.klinikTani.module.authentication.service.AuthenticationService;
import com.company.klinikTani.utility.ModelMapperUtility;
import com.company.klinikTani.model.SuccessResponse;
import com.company.klinikTani.module.authentication.entity.User;
import com.company.klinikTani.module.authentication.entity.UserDetailsImpl;
import com.company.klinikTani.module.authentication.jwt.JwtTokenUtility;
import com.company.klinikTani.module.authentication.presenter.model.LoginRequest;
import com.company.klinikTani.module.authentication.presenter.model.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthenticationController {
    @Autowired
    private ModelMapperUtility modelMapperUtility;
    @Autowired
    private JwtTokenUtility jwtTokenUtility;
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(
            path = "/register",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Object> register(@Valid @ModelAttribute RegisterRequest requestBody) {
        User newUser = modelMapperUtility.initialize()
                .map(requestBody, User.class);
        UserDetails newUserDetails = modelMapperUtility.initialize()
                .map(requestBody, UserDetails.class);
        authenticationService.register(newUser, newUserDetails);
        return new ResponseEntity<>(
                new SuccessResponse(true, "User successfully registered"),
                HttpStatus.CREATED);
    }

    @PostMapping(
            path = "/login",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Object> login(@Valid @ModelAttribute LoginRequest requestBody) {
        authenticationService.login(requestBody.getEmail(), requestBody.getPassword());
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtTokenUtility.generateToken(userDetails))
                .body(new SuccessResponse(true, "Login successful"));
    }
}
