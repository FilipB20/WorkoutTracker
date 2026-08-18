package com.example.workouttracker.controller;

import com.example.workouttracker.dto.UserRequestDTO;
import com.example.workouttracker.dto.UserReturnDTO;
import com.example.workouttracker.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService service) {
        this.userService = service;
    }

    @GetMapping
    public List<UserReturnDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserReturnDTO getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PostMapping
    public void addUser(@RequestBody UserRequestDTO user){
        userService.addNewUser(user);
    }

    @PutMapping("/{id}")
    public void changeUserName(@PathVariable Long id, @RequestParam String newName){
        userService.changeUserName(id,newName);
    }
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
    }

}
