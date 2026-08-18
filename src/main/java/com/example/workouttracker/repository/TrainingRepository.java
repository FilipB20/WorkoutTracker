package com.example.workouttracker.repository;

import com.example.workouttracker.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    public List<Training> getAllTrainingsByUserId(Long id);
}
