package com.example.issuetracker.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.issuetracker.domain.entities.User;
import com.example.issuetracker.repositories.UserRepository;
import com.example.issuetracker.web.dto.UserCreateDTO;
import com.example.issuetracker.web.dto.UserResponseDTO;
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

    public UserResponseDTO createUser(UserCreateDTO dto){
        String hashedPassword = passwordEncoder.encode(dto.password());

        User user = new User( 
            dto.username(),
            hashedPassword,
            dto.email(),
            dto.role()
        );

        User savedUser = userRepository.save(user);

        return userMapper.toResponseDTO(savedUser);
    }

    public UserResponseDTO getUserById(Long id){
        return userRepository.findById(id)
                                .map(userMapper::toResponseDTO)
                                .orElseThrow(() -> new RuntimeException("User with id " + id + " not found."));
    }

    User getUserEntityById(Long id){
        return userRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("User entity with id " + id + " not found."));
    }
}
