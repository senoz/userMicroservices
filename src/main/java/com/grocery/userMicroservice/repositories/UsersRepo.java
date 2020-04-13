package com.grocery.userMicroservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grocery.userMicroservice.dtos.Users;

public interface UsersRepo extends JpaRepository<Users, Long> {
    Users findByUserName(String username);
}