package com.example.WorkoutTracker.Training;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table
public class Training {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "training_sequence"
    )
     private Long id;
     private String type;
     private String description;
     private LocalDate date;

     public Training(Long ID, String Type, String Description, LocalDate Date){
         id=ID;
         type=Type;
         description=Description;
         date=Date;
     }
    public Training(Long ID, String Type, String Description){
        id=ID;
        type=Type;
        description=Description;
        date=LocalDate.now();
    }

    public Training() {
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Training{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
