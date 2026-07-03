package com.example.Student.service;

import com.example.Student.exception.ResourceNotFoundException;
import com.example.Student.model.Course;
import com.example.Student.repository.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepo repo;

    public void addCourse() {
        List<Course> courses = List.of(
            new Course("Java Programming", "java", "Sunil Diwakar", 3),
            new Course("Python for AI & ML", "python", "Ajay Manothra", 4),
            new Course("Spring Boot Microservices", "springboot", "Sharmila Banu", 7),
            new Course("Data Structures & Algorithms", "dsa", "Lily", 10),
            new Course("Full Stack Development (React + Spring Boot)", "fullstack", "Vikram Rao", 8),
            new Course("DevOps with Docker & Kubernetes", "devops", "Michael Chen", 6),
            new Course("AWS Cloud Practitioner", "aws", "Ananya Sharma", 5),
            new Course("Cyber Security Basics", "cybersecurity", "David John", 4),
            new Course("Data Science with Python", "datascience", "Neha Patel", 6),
            new Course("System Design Fundamentals", "systemdesign", "Karthik Subramanian", 12)
        );

        repo.saveAll(courses);
    }

    public List<Course> getAllCourse() {
        List<Course> courses = repo.findAll();
        if(courses.isEmpty()){
            throw new ResourceNotFoundException("Courses Not Available");
        }
        return courses;
    }

    public Course getByCode(String code){
        Course course = repo.findByCode(code);
        if(course==null){
            throw new ResourceNotFoundException("Course Not Found with code: "+code);
        }
        return course;
    }

}
