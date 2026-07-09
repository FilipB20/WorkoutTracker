package com.example.WorkoutTracker.Training;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TrainingService {
    private final TrainingRepository trainingRepository;

    @Autowired
    public TrainingService(TrainingRepository trainingRepository){
        this.trainingRepository=trainingRepository;
    }

    public List<Training> getAllTrainings(){
        return trainingRepository.findAll();
    }

    public Training getTrainingByDate(LocalDate date){

    }
}
