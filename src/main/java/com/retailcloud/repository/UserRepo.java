package com.retailcloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retailcloud.model.Users;


@Repository
public interface UserRepo extends JpaRepository<Users, Long> {

    Users findByUsername(String username);
    }