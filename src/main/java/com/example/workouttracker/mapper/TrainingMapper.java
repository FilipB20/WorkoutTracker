package com.example.workouttracker.mapper;

import com.example.workouttracker.dto.TrainingReturnDTO;
import com.example.workouttracker.entity.Training;

public class TrainingMapper {
    public static TrainingReturnDTO TrainingToDto(Training training){
        return new TrainingReturnDTO(training.getId(), training.getType(), training.getDescription(), training.getDate());
    }
}
