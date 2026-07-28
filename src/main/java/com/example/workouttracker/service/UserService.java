package com.example.workouttracker.service;

import com.example.workouttracker.dto.UserDTO;
import com.example.workouttracker.dto.UserRequestDTO;
import com.example.workouttracker.dto.UserReturnDTO;
import com.example.workouttracker.entity.Training;
import com.example.workouttracker.entity.User;
import com.example.workouttracker.mapper.UserMapper;
import com.example.workouttracker.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.userRepository = repository;
        this.userMapper=mapper;
    }

    public List<UserDTO> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getDob()
                )).toList();
    }
    public void addTrainingToUserId(Long id, Training training){

    }

    public void addNewUser(UserRequestDTO user){
        userRepository.save(new User(
                user.username(),
                user.password(),
                user.email(),
                user.dob()
        ));
    }

    public UserDTO changeUserName(Long id, String newName) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found with id: " + id));
        userToUpdate.setUsername(newName);
        User updatedUser = userRepository.save(userToUpdate);
        return userMapper.toDto(updatedUser);

    }
}
