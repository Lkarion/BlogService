package com.example.blogservice.repository;

import com.example.blogservice.entity.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<BlogUser, Long> {

    Boolean existsByUsername(String username);
    BlogUser findByUsername(String username);
}
