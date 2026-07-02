package com.example.Student.controller;


import com.example.Student.model.ApiResponse;
import com.example.Student.model.Course;
import com.example.Student.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v01/course")
public class CourseControllerJPA {

    @Autowired
    private CourseService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Course>>> getCourse(){
        List<Course> courses = service.getAllCourse();
        if(courses.isEmpty()){
            return new ResponseEntity<>(new ApiResponse<>(
                    "Courses Not Found", null
            ),HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new ApiResponse<>("Course Found",courses), HttpStatus.OK);
    }

    @GetMapping("/filtersBy")
    public ResponseEntity<ApiResponse<Course>> getByTitle(@RequestParam("code") String title){
        Course course = service.getByTitle(title);
        if(course==null){
            return new ResponseEntity<>(new ApiResponse<>(
                    "Course Not Found, Please Check Title", null
            ),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiResponse<>("Course Found",course), HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<List<Course>>> addCourse(){
        service.addCourse();
        return new ResponseEntity<>(new ApiResponse<>("Successfully Added",service.getAllCourse()), HttpStatus.OK);
    }

}
