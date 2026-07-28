package com.example.workouttracker.entity;

import com.example.workouttracker.enums.Role;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

import static com.example.workouttracker.enums.Role.ROLE_USER;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "training_sequence"
    )
    private Long id;
    private String username;
    private String password;
    private String email;
    private LocalDate dob;
    private Role role;
    @OneToMany(mappedBy = "user")
    private List<Training> trainingList;

    public User() {
    }

    public User(String username, String password, String email, LocalDate dob) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.dob = dob;
        this.role = ROLE_USER;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Training> getTrainingList() {
        return trainingList;
    }

    public void setTrainingList(List<Training> trainingList) {
        this.trainingList = trainingList;
    }

    public void addTraining(Training training){
        trainingList.add(training);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", role=" + role +
                ", trainingList=" + trainingList +
                '}';
    }
}
