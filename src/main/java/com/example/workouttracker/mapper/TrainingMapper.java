package com.example.workouttracker.mapper;

import com.example.workouttracker.dto.TrainingRequestDTO;
import com.example.workouttracker.dto.TrainingReturnDTO;
import com.example.workouttracker.entity.Training;
import com.example.workouttracker.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

public class TrainingMapper {
    public static TrainingReturnDTO TrainingToDto(Training training){
        return new TrainingReturnDTO(training.getId(), training.getType(), training.getDescription(), training.getDate());
    }
    public static Training trainingRequestToTraining(TrainingRequestDTO trainingRequestDTO, User user){
        return new Training(null,
                trainingRequestDTO.type,
                trainingRequestDTO.description,
                trainingRequestDTO.date,
                user);
    }
}
