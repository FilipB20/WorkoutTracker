package com.example.workouttracker.service;

import com.example.workouttracker.config.JwtService;
import com.example.workouttracker.dto.*;
import com.example.workouttracker.entity.User;
import com.example.workouttracker.enums.Role;
import com.example.workouttracker.exceptions.UserWithIdNotFound;
import com.example.workouttracker.mapper.UserMapper;
import com.example.workouttracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class UserService {
    private final UserRepository _userRepository;
    private final UserMapper _userMapper;
    private final PasswordEncoder _passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager _authenticationManager;

    public UserService(UserRepository repository, UserMapper mapper, PasswordEncoder encoder, JwtService jwtService, AuthenticationManager manager) {
        this._userRepository = repository;
        this._userMapper =mapper;
        this._passwordEncoder =encoder;
        this.jwtService=jwtService;
        this._authenticationManager =manager;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        _authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        var user = _userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UsernameNotFoundException("Korisnik nije pronađen"));

        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public List<UserReturnDTO> getAllUsers(){
        return _userRepository.findAll()
                .stream()
                .map(user -> new UserReturnDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getDob()
                )).toList();
    }

    public void addNewUser(UserRequestDTO user){
        _userRepository.save(new User(
                user.username(),
                _passwordEncoder.encode(user.password()),
                user.email(),
                user.dob()
        ));
        log.info("Dodan je novi korisnik!");
    }

    public UserDTO changeUserName(Long id, String newName) throws UserWithIdNotFound {
        User userToUpdate = _userRepository.findById(id)
                .orElseThrow(()->new UserWithIdNotFound(id));
        userToUpdate.setUsername(newName);
        User updatedUser = _userRepository.save(userToUpdate);
        return _userMapper.toDto(updatedUser);

    }
    //demonstracija asinkrone metode, previse je za potrebe jednostavne crud aplikacije
    @Async
    public CompletableFuture<UserReturnDTO> getUserById(Long id) throws UserWithIdNotFound {
        log.info("Vraća se user po ID-u {} u vrijeme:{}",id, LocalDateTime.now());

        User userById= _userRepository.findById(id)
                .orElseThrow(() -> new UserWithIdNotFound(id));
        return CompletableFuture.completedFuture(UserMapper.toReturnDto(userById));
    }
    public void deleteUserById(Long id) throws UserWithIdNotFound {
        log.info("Briše se user po ID-u {} u vrijeme {}",id,LocalDate.now());
        if (!_userRepository.existsById(id)) {
            throw new UserWithIdNotFound(id);
        }
            _userRepository.deleteById(id);
            log.info("Obrisan user s ID {}",id);
    }
    @Transactional
    public void AssignAdminRoleToUser(Long id) throws UserWithIdNotFound {
        log.info("Dodavanje admin role useru s ID: {}", id);;
        User userById= _userRepository.findById(id)
                .orElseThrow(() -> new UserWithIdNotFound(id));
        userById.setRole(Role.ROLE_ADMIN);
        _userRepository.save(userById);
    }
}
