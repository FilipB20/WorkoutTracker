package com.example.workouttracker.service;

import com.example.workouttracker.dto.UserDTO;
import com.example.workouttracker.dto.UserRequestDTO;
import com.example.workouttracker.dto.UserReturnDTO;
import com.example.workouttracker.entity.Training;
import com.example.workouttracker.entity.User;
import com.example.workouttracker.mapper.UserMapper;
import com.example.workouttracker.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.userRepository = repository;
        this.userMapper=mapper;
    }

    public List<UserReturnDTO> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(user -> new UserReturnDTO(
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
        log.info("Dodan je novi korisnik!");
    }

    public UserDTO changeUserName(Long id, String newName) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found with id: " + id));
        userToUpdate.setUsername(newName);
        User updatedUser = userRepository.save(userToUpdate);
        return userMapper.toDto(updatedUser);

    }
    public UserReturnDTO getUserById(Long id){
        log.info("Vraća se user po ID-u {} u vrijeme:{}",id,LocalDate.now());
        User userById=userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Korisnik s ID-jem " + id + " nije pronađen!"));
        return UserMapper.toReturnDto(userById);
    }
    public void deleteUserById(Long id){
        log.info("Briše se user po ID-u {} u vrijeme {}",id,LocalDate.now());
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Korisnik s ID-jem "+id+" nije pronađen!");
        }
            userRepository.deleteById(id);
            log.info("Obrisan user s ID {}",id);
    }
}
