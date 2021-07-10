package com.company.klinikTani.module.authentication.repository;

import com.company.klinikTani.module.authentication.entity.Role;
import com.company.klinikTani.module.authentication.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByRoleName(RoleEnum roleEnum);
}
