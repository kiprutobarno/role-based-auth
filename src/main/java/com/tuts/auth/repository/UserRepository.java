package com.tuts.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tuts.auth.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmail(String email);

    boolean existsByEmail(String email);
}