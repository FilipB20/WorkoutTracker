package com.example.workouttracker.mapper;

import com.example.workouttracker.dto.UserDTO;
import com.example.workouttracker.dto.UserRequestDTO;
import com.example.workouttracker.dto.UserReturnDTO;
import com.example.workouttracker.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // Pretvori Entity -> UserDTO
    public UserDTO toDto(User user) {
        if (user == null) return null;
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getDob()
        );
    }

    // Pretvori Entity -> UserReturnDTO
    public static UserReturnDTO toReturnDto(User user) {
        if (user == null) return null;
        return new UserReturnDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getDob()
        );
    }

    // Pretvori UserRequestDTO -> Entity
    public User toEntity(UserRequestDTO dto) {
        if (dto == null) return null;
        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setEmail(dto.email());
        user.setDob(dto.dob());
        return user;
    }
}