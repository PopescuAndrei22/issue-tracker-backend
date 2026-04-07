package com.example.issuetracker.web.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.issuetracker.services.ProjectService;
import com.example.issuetracker.web.dto.ProjectCreateDTO;
import com.example.issuetracker.web.dto.ProjectResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void createProjectShouldReturnCode201() throws Exception{
        ProjectCreateDTO requestDto = new ProjectCreateDTO("My project name", 1L, "My project description");
        ProjectResponseDTO responseDto = new ProjectResponseDTO(1L, "My project name", 1L, "My project description");

        when(projectService.createProject(requestDto)).thenReturn(responseDto);

        mockMvc.perform(post("/api/projects")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", containsString("/api/projects/1")))
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("My project name"))
            .andExpect(jsonPath("$.ownerId").value(1L))
            .andExpect(jsonPath("$.description").value("My project description"));
    }

    @Test
    @WithMockUser
    void createProjectWithInvalidDtoShouldReturnCode400() throws Exception {
        ProjectCreateDTO invalidDto = new ProjectCreateDTO(null, null, null);

        mockMvc.perform(post("/api/projects")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
            .andExpect(status().isBadRequest());
    }
}
