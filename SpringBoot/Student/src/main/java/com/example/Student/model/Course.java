package com.example.Student.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Course {
    @Id
    @GeneratedValue
    private int id;
    private String title;
    private String code;
    private String staff;
    private int duration;

    public Course(String title, String code, String staff, int duration) {
        this.title = title;
        this.code = code;
        this.staff = staff;
        this.duration = duration;
    }
}
