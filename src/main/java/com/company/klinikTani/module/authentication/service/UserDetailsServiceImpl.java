package com.company.klinikTani.module.authentication.service;

import com.company.klinikTani.exception.AuthenticateFailedException;
import com.company.klinikTani.module.authentication.entity.User;
import com.company.klinikTani.module.authentication.entity.UserDetailsImpl;
import com.company.klinikTani.module.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticateFailedException("Incorrect email or password"));
        return new UserDetailsImpl(user);
    }
}
