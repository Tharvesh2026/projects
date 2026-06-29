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
    public Student addMock() {
        return service.mockData();
    }
}