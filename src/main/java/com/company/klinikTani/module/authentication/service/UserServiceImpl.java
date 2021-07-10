package com.company.klinikTani.module.authentication.service;

import com.company.klinikTani.exception.ResourceNotFoundException;
import com.company.klinikTani.module.authentication.entity.User;
import com.company.klinikTani.module.authentication.entity.UserDetails;
import com.company.klinikTani.module.authentication.model.UserDetailsDto;
import com.company.klinikTani.module.authentication.repository.UserDetailsRepository;
import com.company.klinikTani.module.authentication.repository.UserRepository;
import com.company.klinikTani.utility.ModelMapperUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private ModelMapperUtility modelMapperUtility;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    public UserDetailsDto getUserDetails() {
        User signedInUser = getSignedInUser();
        UserDetails userDetails = userDetailsRepository.findByUser(signedInUser)
                .orElseThrow(() -> new ResourceNotFoundException("User's details not found"));

        UserDetailsDto userDetailsDto = modelMapperUtility.initialize()
                .map(userDetails, UserDetailsDto.class);
        userDetailsDto.setEmail(signedInUser.getEmail());

        return userDetailsDto;
    }

    private User getSignedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

}
