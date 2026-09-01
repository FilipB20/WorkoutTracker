package com.example.workouttracker.dto;

import com.example.workouttracker.entity.Training;
import com.example.workouttracker.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

public class TrainingReturnDTO {
    private Long id;
    private String type;
    private String description;
    private LocalDate date;

    public TrainingReturnDTO(Long id, String type, String description, LocalDate date) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.date = date;
    }


}
