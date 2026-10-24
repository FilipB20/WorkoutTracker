package com.example.workouttracker.controller;

import com.example.workouttracker.dto.UserDTO;
import com.example.workouttracker.dto.UserRequestDTO;
import com.example.workouttracker.dto.UserReturnDTO;
import com.example.workouttracker.dto.UserWithIdNotFound;
import com.example.workouttracker.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/users")
@EnableMethodSecurity
public class UserController {
    private final UserService _userService;

    public UserController(UserService service)
    {
        this._userService = service;
    }

    @GetMapping
    public ResponseEntity<List<UserReturnDTO>> getAllUsers()
    {
        return ResponseEntity.ok(_userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<UserReturnDTO>> getUserById(@PathVariable Long id) throws UserWithIdNotFound
    {
        return _userService.getUserById(id).thenApply(ResponseEntity::ok);
    }

    @PostMapping
    public ResponseEntity<Void> addUser(@RequestBody UserRequestDTO user)
    {
        _userService.addNewUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> changeUserName(@PathVariable Long id, @RequestParam String newName) throws UserWithIdNotFound
    {
        return ResponseEntity.ok(_userService.changeUserName(id,newName));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) throws UserWithIdNotFound
    {
        _userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
