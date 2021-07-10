package com.company.klinikTani.utility;

import com.company.klinikTani.exception.ResourceNotFoundException;
import com.company.klinikTani.module.authentication.entity.User;
import com.company.klinikTani.module.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserUtility {
    @Autowired
    private UserRepository userRepository;

    public User getSignedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
