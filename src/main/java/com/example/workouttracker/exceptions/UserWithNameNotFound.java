package com.example.workouttracker.exceptions;

public class UserWithNameNotFound extends Exception {
    public UserWithNameNotFound(String name) {
        super("Nije pronaden user s nazivom: "+ name);
    }
}
