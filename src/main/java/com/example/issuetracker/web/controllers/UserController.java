package com.example.issuetracker.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.issuetracker.services.UserService;
import com.example.issuetracker.web.dto.UserCreateDTO;
import com.example.issuetracker.web.dto.UserResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserCreateDTO dto){
        return ResponseEntity.ok().build();
    }
}
