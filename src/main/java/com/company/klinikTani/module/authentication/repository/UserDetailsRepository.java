package com.company.klinikTani.module.authentication.repository;

import com.company.klinikTani.module.authentication.entity.User;
import com.company.klinikTani.module.authentication.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, UUID> {
    Optional<UserDetails> findByMobileNumber(String mobileNumber);
    Optional<UserDetails> findByUser(User user);
}
