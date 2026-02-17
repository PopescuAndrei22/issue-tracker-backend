package com.example.issuetracker.services;

import org.springframework.stereotype.Service;

import com.example.issuetracker.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
}
