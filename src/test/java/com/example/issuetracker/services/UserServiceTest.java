package com.example.issuetracker.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.issuetracker.domain.entities.User;
import com.example.issuetracker.domain.models.Role;
import com.example.issuetracker.exceptions.ResourceNotFoundException;
import com.example.issuetracker.repositories.UserRepository;
import com.example.issuetracker.web.dto.UserCreateDTO;
import com.example.issuetracker.web.mappers.UserMapper;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateTheUserSuccessfully(){
        String username = "bob";
        String rawPassword = "simplepassword123";
        String hashedPassword = "encoded_hash_123";
        String email = "myemail@company.com";
        Role role = Role.USER;
        UserCreateDTO dto = new UserCreateDTO(
            username,
            email,
            rawPassword,
            role
        );
        when(passwordEncoder.encode(rawPassword)).thenReturn(hashedPassword);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        userService.createUser(dto);

        verify(passwordEncoder).encode(rawPassword);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertEquals(hashedPassword, savedUser.getPassword());
        verify(userMapper).toResponseDTO(any(User.class));
    }

    @Test
    void shouldThrowWhenUserDoesNotExist(){
        Long userId = 99L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> 
                userService.getUserById(userId))
            .isInstanceOf(ResourceNotFoundException.class);

        assertThatThrownBy(() -> 
                userService.getUserEntityById(userId))
            .isInstanceOf(ResourceNotFoundException.class);

        verifyNoInteractions(userMapper);
    }
}
