package com.example.Student.controller;

import com.example.Student.model.Student;
import com.example.Student.service.StudentJPAService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v01/students")
public class StudentJPAController {

    private final StudentJPAService service;

    // CREATE
    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return service.addStudent(student);
    }

    // READ ALL
    @GetMapping
    public List<Student> getAll() {
        return service.getAllStudents();
    }

    // READ ONE
    @GetMapping("/{rollNo}")
    public Student getOne(@PathVariable int rollNo) {
        return service.getStudent(rollNo);
    }

    @GetMapping("/filtersBy")
    public List<Student> findByCourse(@RequestParam("course") String tech){
        return service.findByTech(tech);
    }

    @PostMapping("/filtersBy")
    public List<Student> findByGenderandCourse(@RequestParam("course") String tech,
                                               @RequestParam("gender") String gender){
        return service.findByGenderAndCourse(tech,gender);
    }
    // UPDATE
    @PutMapping("/{rollNo}")
    public Student update(@PathVariable int rollNo,
                          @RequestBody Student student) {
        return service.updateStudent(rollNo, student);
    }
    // DELETE
    @DeleteMapping("/{rollNo}")
    public String delete(@PathVariable int rollNo) {
        return service.deleteStudent(rollNo);
    }

    // MOCK DATA ENDPOINT
    @PostMapping("/addMock")
    public List<Student> addMock() {
        return service.mockData();
    }
}