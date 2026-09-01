package com.example.workouttracker.dto;

public record AuthenticationRequest(
        String username,
        String password
) {}