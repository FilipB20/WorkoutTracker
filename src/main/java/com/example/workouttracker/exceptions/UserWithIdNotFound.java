package com.example.workouttracker.exceptions;

public class UserWithIdNotFound extends Exception {
    public UserWithIdNotFound(Long id) {
        super("Nije pronaden user s ID: "+ id);
    }
}
