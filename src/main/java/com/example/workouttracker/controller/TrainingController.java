package com.example.workouttracker.controller;

import com.example.workouttracker.dto.TrainingRequestDTO;
import com.example.workouttracker.dto.TrainingReturnDTO;
import com.example.workouttracker.exceptions.UserWithIdNotFound;
import com.example.workouttracker.exceptions.UserWithNameNotFound;
import com.example.workouttracker.service.TrainingService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;


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
    public CompletableFuture<ResponseEntity<List<TrainingReturnDTO>>> getMyTrainings(Authentication authentication) throws UserWithNameNotFound
    {
        return trainingService.getAllTrainingsByUsername(authentication.getName())
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping("/me")
    public ResponseEntity<TrainingReturnDTO> addTrainingToMe(Authentication authentication, @RequestBody TrainingRequestDTO training) throws UserWithNameNotFound {
        TrainingReturnDTO created = trainingService.createNewTrainingToMe(authentication,training);
        URI location = URI.create("/api/trainings/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("user/{id}")
    public ResponseEntity<Void> addTrainingToUser(@PathVariable Long id, @RequestBody TrainingRequestDTO training)throws UserWithIdNotFound
    {
        trainingService.addNewTrainingToUser(id,training);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("user/{id}")
    public ResponseEntity<List<TrainingReturnDTO>> getAllTrainingsByUserId(@PathVariable Long id) throws UserWithIdNotFound
    {
        return ResponseEntity.ok(trainingService.getAllTrainingsByUserId(id));
    }
}
