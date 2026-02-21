package com.example.issuetracker.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.issuetracker.repositories.UserRepository;
import com.example.issuetracker.web.mappers.UserMapper;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService
    (
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        UserMapper userMapper
    ){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }
}
