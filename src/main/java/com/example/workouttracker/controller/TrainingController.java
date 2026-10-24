package com.example.workouttracker.controller;

import com.example.workouttracker.dto.TrainingReturnDTO;
import com.example.workouttracker.entity.Training;
import com.example.workouttracker.service.TrainingService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("api/v1/trainings")
public class TrainingController {
    TrainingService trainingService;
    Authentication authentication;

    @Autowired
    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @GetMapping("/me")
    public ResponseEntity<List<TrainingReturnDTO>> getMyTrainings(Authentication authentication){
        return ResponseEntity.ok(trainingService.getAllTrainingsByUsername(authentication));
    }

    @PostMapping("/me")
    public ResponseEntity<TrainingReturnDTO> addTrainingToMe(Authentication authentication, @RequestBody Training training){
        TrainingReturnDTO created = trainingService.createNewTrainingToMe(authentication,training);
        URI location = URI.create("/api/trainings/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("user/{id}")
    public ResponseEntity addTrainingToUser(@PathVariable Long id, @RequestBody Training training){
        trainingService.addNewTrainingToUser(id,training);
        return ResponseEntity.ok().build();
    }

    @GetMapping("user/{id}")
    public List<TrainingReturnDTO> getAllTrainingsByUserId(@PathVariable Long id){
        return trainingService.getAllTrainingsByUserId(id);
    }
}
