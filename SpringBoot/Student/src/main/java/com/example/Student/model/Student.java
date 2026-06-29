package com.example.Student.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Student {
    private String name;
    private String gender;
    @Id
    private int rollNo;
    private List<String> course;
}
