package com.coworkly.domain.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coworkly.domain.auth.user.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
