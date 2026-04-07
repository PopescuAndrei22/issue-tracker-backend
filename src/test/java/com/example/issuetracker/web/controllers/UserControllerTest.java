package com.example.issuetracker.web.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.issuetracker.domain.models.Role;
import com.example.issuetracker.services.UserService;
import com.example.issuetracker.web.dto.UserCreateDTO;
import com.example.issuetracker.web.dto.UserResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void createUserShouldReturnCode201() throws Exception{
        UserCreateDTO requestDto = new UserCreateDTO("bobname", "bobemail@gmail.com", "hashedpassword123", Role.USER);
        UserResponseDTO responseDto = new UserResponseDTO(1L, "bobname", "bobemail@gmail.com", Role.USER);

        when(userService.createUser(any(UserCreateDTO.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/users")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", containsString("/api/users/1")))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.username").value("bobname"))
            .andExpect(jsonPath("$.email").value("bobemail@gmail.com"))
            .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    @WithMockUser
    void createUserWithInvalidDtoShouldReturnCode400() throws Exception {
        UserCreateDTO invalidDto = new UserCreateDTO("bob", "not-an-email", "", Role.USER);

        mockMvc.perform(post("/api/users")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
            .andExpect(status().isBadRequest());
    }
}
