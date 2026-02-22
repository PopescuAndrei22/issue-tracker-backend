package com.example.issuetracker.web.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import com.example.issuetracker.domain.entities.User;
import com.example.issuetracker.web.dto.UserResponseDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserResponseDTO toResponseDTO(User user);

    List<UserResponseDTO> toResponseDTOList(List<User> users);
}
