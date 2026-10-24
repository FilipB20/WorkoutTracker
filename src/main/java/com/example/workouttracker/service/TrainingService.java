package com.example.workouttracker.service;

import com.example.workouttracker.dto.TrainingReturnDTO;
import com.example.workouttracker.entity.Training;
import com.example.workouttracker.entity.User;
import com.example.workouttracker.mapper.TrainingMapper;
import com.example.workouttracker.repository.TrainingRepository;
import com.example.workouttracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    public TrainingReturnDTO createNewTrainingToMe(Authentication authentication,Training training){
        User user = userRepository.findByUsername(authentication.name())
                .orElseThrow(()-> new RuntimeException("Nije pronađen user sa nazivom"+authentication.name()));
        training.setUser(user);
        trainingRepository.save(training);
        return TrainingMapper.TrainingToDto(training);
    }

    public void addNewTrainingToUser(Long id,Training training){
        log.info("Pokreće se dodavanje treninga korisniku {}",id);
        User userById = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Nije pronađen user sa ID-jem"+id));
        training.setUser(userById);
        trainingRepository.save(training);
    }

    public List<TrainingReturnDTO> getAllTrainingsByUserId(Long id){
        log.info("Vraćaju se svi treninzi koji pripadaju korisniku s ID-jem {}",id);
        return trainingRepository.getAllTrainingsByUserId(id)
                .stream()
                .map(TrainingMapper::TrainingToDto)
                .toList();
    }

    public List<TrainingReturnDTO> getAllTrainingsByUsername(Authentication authentication) {
        log.info("Vracaju se svi treninzi za prijavljenog usera: "+authentication.name());
        return trainingRepository.getAllTrainingsByUserUsername(authentication.name())
                .stream()
                .map(TrainingMapper::TrainingToDto)
                .toList();

    }
}
