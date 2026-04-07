package com.example.issuetracker.web.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
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

import com.example.issuetracker.domain.models.IssueType;
import com.example.issuetracker.domain.models.Priority;
import com.example.issuetracker.domain.models.Status;
import com.example.issuetracker.services.IssueService;
import com.example.issuetracker.web.dto.IssueCreateDTO;
import com.example.issuetracker.web.dto.IssueResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(IssueController.class)
class IssueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IssueService issueService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void createIssueShouldReturnCode201() throws Exception{
        IssueCreateDTO requestDto = new IssueCreateDTO("My title", "My description", IssueType.BUG, Priority.HIGH, 1L, 2L, 5L);
        IssueResponseDTO responseDto = new IssueResponseDTO(1L, "My title", "My description", IssueType.BUG, Priority.HIGH, Status.OPEN, 1L, 2L, 5L);

        when(issueService.createIssue(requestDto)).thenReturn(responseDto);

        mockMvc.perform(post("/api/issues")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", containsString("/api/issues/1")))
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.title").value("My title"))
            .andExpect(jsonPath("$.description").value("My description"))
            .andExpect(jsonPath("$.type").value("BUG"))
            .andExpect(jsonPath("$.priority").value("HIGH"))
            .andExpect(jsonPath("$.status").value("OPEN"))
            .andExpect(jsonPath("$.reporterId").value(1L))
            .andExpect(jsonPath("$.assigneeId").value(2L))
            .andExpect(jsonPath("$.projectId").value(5L));
    }

    @Test
    @WithMockUser
    void createIssueWithInvalidDtoShouldReturnCode400() throws Exception {
        IssueCreateDTO invalidDto = new IssueCreateDTO("My title", "My description", IssueType.BUG, Priority.HIGH, null, 2L, 5L);

        mockMvc.perform(post("/api/issues")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
            .andExpect(status().isBadRequest());
    }
}
