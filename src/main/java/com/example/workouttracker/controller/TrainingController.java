package com.example.workouttracker.controller;

import com.example.workouttracker.entity.Training;
import com.example.workouttracker.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/trainings")
public class TrainingController {
    TrainingService trainingService;

    @Autowired
    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping("user/{id}")
    public void addTrainingToUser(@PathVariable Long id,@RequestBody Training training){
        trainingService.addNewTrainingToUser(id,training);
    }
    @GetMapping("user/{id}")
    public List<Training> getAllTrainingsByUserId(@PathVariable Long id){
        return trainingService.getAllTrainingsByUserId(id);
    }
}
