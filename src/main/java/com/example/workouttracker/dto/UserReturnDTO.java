package com.example.workouttracker.dto;

import com.example.workouttracker.entity.Training;
import com.example.workouttracker.enums.Role;
import com.example.workouttracker.entity.User;

import java.time.LocalDate;
import java.util.List;

public record UserReturnDTO (
        Long id,
        String username,
        String email,
        LocalDate dob
){

}
