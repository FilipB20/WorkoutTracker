package com.example.workouttracker.dto;

import com.example.workouttracker.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

public class TrainingRequestDTO {
    public String type;
    public String description;
    public LocalDate date;
}
