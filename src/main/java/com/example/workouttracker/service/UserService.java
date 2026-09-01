package com.example.workouttracker.service;

import com.example.workouttracker.config.JwtService;
import com.example.workouttracker.dto.*;
import com.example.workouttracker.entity.Training;
import com.example.workouttracker.entity.User;
import com.example.workouttracker.mapper.UserMapper;
import com.example.workouttracker.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository repository, UserMapper mapper, PasswordEncoder encoder, JwtService jwtService, AuthenticationManager manager) {
        this.userRepository = repository;
        this.userMapper=mapper;
        this.passwordEncoder=encoder;
        this.jwtService=jwtService;
        this.authenticationManager=manager;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        var user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UsernameNotFoundException("Korisnik nije pronađen"));

        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
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

    public void addNewUser(UserRequestDTO user){
        userRepository.save(new User(
                user.username(),
                passwordEncoder.encode(user.password()),
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
