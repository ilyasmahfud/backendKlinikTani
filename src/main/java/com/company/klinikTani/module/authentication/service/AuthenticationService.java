package com.company.klinikTani.module.authentication.service;

import com.company.klinikTani.module.authentication.entity.User;
import com.company.klinikTani.module.authentication.entity.UserDetails;

public interface AuthenticationService {
    void register(User userRequestBody, UserDetails userDetailsRequestBody);
    void login(String email, String password);
}
