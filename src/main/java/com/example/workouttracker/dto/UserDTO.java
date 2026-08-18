package com.example.workouttracker.dto;

import com.example.workouttracker.entity.Training;
import com.example.workouttracker.enums.Role;
import com.example.workouttracker.entity.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

public record UserDTO (
        Long id,
        String username,
        String email,
        LocalDate dob
){

}