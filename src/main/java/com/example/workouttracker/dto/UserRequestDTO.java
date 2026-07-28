package com.example.workouttracker.dto;

import com.example.workouttracker.entity.Training;
import com.example.workouttracker.enums.Role;
import com.example.workouttracker.entity.User;

import java.time.LocalDate;
import java.util.List;

public record UserRequestDTO (
        String username,
        String password,
        String email,
        LocalDate dob
){

}
