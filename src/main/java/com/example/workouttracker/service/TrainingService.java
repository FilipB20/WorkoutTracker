package com.example.workouttracker.service;

import com.example.workouttracker.dto.TrainingRequestDTO;
import com.example.workouttracker.dto.TrainingReturnDTO;
import com.example.workouttracker.exceptions.UserWithIdNotFound;
import com.example.workouttracker.exceptions.UserWithNameNotFound;
import com.example.workouttracker.entity.Training;
import com.example.workouttracker.entity.User;
import com.example.workouttracker.mapper.TrainingMapper;
import com.example.workouttracker.repository.TrainingRepository;
import com.example.workouttracker.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private final UserRepository userRepository;

    @Autowired
    public TrainingService(TrainingRepository trainingRepository, UserRepository userRepository){
        this.trainingRepository=trainingRepository;
        this.userRepository = userRepository;
    }

    public List<Training> getAllTrainings(){
        return trainingRepository.findAll();
    }

    @Transactional
    public TrainingReturnDTO createNewTrainingToMe(Authentication authentication,TrainingRequestDTO trainingRequestDTO) throws UserWithNameNotFound {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(()-> new UserWithNameNotFound(authentication.getName()));
        Training training = TrainingMapper.trainingRequestToTraining(trainingRequestDTO,user);
        trainingRepository.save(training);
        return TrainingMapper.TrainingToDto(training);
    }

    @Transactional
    public void addNewTrainingToUser(Long id, TrainingRequestDTO trainingRequestDTO) throws UserWithIdNotFound {
        log.info("Pokreće se dodavanje treninga korisniku {}",id);
        User userById = userRepository.findById(id)
                .orElseThrow(()-> new UserWithIdNotFound(id));
        Training training = TrainingMapper.trainingRequestToTraining(trainingRequestDTO,userById);
        trainingRepository.save(training);
    }

    public List<TrainingReturnDTO> getAllTrainingsByUserId(Long id) throws UserWithIdNotFound {
        log.info("Vraćaju se svi treninzi koji pripadaju korisniku s ID-jem {}",id);
        if (!userRepository.existsById(id)) {
            throw new UserWithIdNotFound(id);
        }
        return trainingRepository.getAllTrainingsByUserId(id)
                .stream()
                .map(TrainingMapper::TrainingToDto)
                .toList();
    }

    @Async
    public CompletableFuture<List<TrainingReturnDTO>> getAllTrainingsByUsername(String name) throws UserWithNameNotFound {
        log.info("Vracaju se svi treninzi za prijavljenog usera: "+name);
        if (!userRepository.existsByUsername(name)) {
            throw new UserWithNameNotFound(name);
        }
        return CompletableFuture.completedFuture(trainingRepository.getAllTrainingsByUserUsername(name)
                .stream()
                .map(TrainingMapper::TrainingToDto)
                .toList());

    }
}
