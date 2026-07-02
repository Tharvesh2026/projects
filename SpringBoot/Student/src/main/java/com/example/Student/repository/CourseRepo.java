package com.example.Student.repository;

import com.example.Student.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepo extends JpaRepository<Course, Integer> {
    Course findByCode(String code);
}
