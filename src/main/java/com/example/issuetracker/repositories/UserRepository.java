package com.example.issuetracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.issuetracker.domain.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
