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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
