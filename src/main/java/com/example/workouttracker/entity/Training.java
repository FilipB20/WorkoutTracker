package com.example.workouttracker.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     private String type;
     private String description;
     private LocalDate date;
     @ManyToOne
     @JoinColumn(name="user_id")
     private User user;

    public Training() {
    }

    public Training(Long id, String type, String description, LocalDate date, User user) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.date = date;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Training{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", user=" + user +
                '}';
    }
}
