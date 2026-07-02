package com.example.Student.controller;


import com.example.Student.model.ApiResponse;
import com.example.Student.model.Course;
import com.example.Student.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v01/course")
public class CourseController {

    @Autowired
    private CourseService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Course>>> getCourse(){
        List<Course> courses = service.getAllCourse();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Course Found",courses));
    }

    @GetMapping("/filtersBy")
    public ResponseEntity<ApiResponse<Course>> getByTitle(@RequestParam("code") String title){
        Course course = service.getByTitle(title);
        return ResponseEntity.status(HttpStatus.FOUND).body(new ApiResponse<>("Course Found",course));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<List<Course>>> addCourse(){
        service.addCourse();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfully Added",service.getAllCourse()));
    }

}
