package com.example.Student.controller;

import com.example.Student.model.ApiResponse;
import com.example.Student.model.Student;
import com.example.Student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v01/students")
@CrossOrigin("*")
public class StudentController {

    private final StudentService service;

    // CREATE
    @PostMapping
    public ResponseEntity<ApiResponse<Student>> addStudent(@RequestBody Student student) {

        Student saved = service.addStudent(student);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Student added successfully", saved));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<ApiResponse<List<Student>>> getAll() {

        List<Student> students = service.getAllStudents();

        return ResponseEntity.ok(
                new ApiResponse<>("Students fetched successfully", students)
        );
    }

    // GET ONE
    @GetMapping("/{rollNo}")
    public ResponseEntity<ApiResponse<Student>> getOne(@PathVariable int rollNo) {

        Student student = service.getStudent(rollNo);

        return ResponseEntity.ok(
                new ApiResponse<>("Student fetched successfully", student)
        );
    }

    // CLEAR ALL
    @PostMapping("/clearAll")
    public ResponseEntity<ApiResponse<String>> deleteAll(@RequestParam boolean confirm) {

        String status = service.deleteMock(confirm);

        return ResponseEntity.ok(
                new ApiResponse<>("Student data cleared", status)
        );
    }

    // FILTER BY COURSE
    @GetMapping("/filtersBy")
    public ResponseEntity<ApiResponse<List<Student>>> findByCourse(@RequestParam String course) {

        List<Student> students = service.findByTech(course);

        return ResponseEntity.ok(
                new ApiResponse<>("Filtered students fetched successfully", students)
        );
    }

    // FILTER BY GENDER + COURSE
    @PostMapping("/filtersBy")
    public ResponseEntity<ApiResponse<List<Student>>> findByGenderAndCourse(
            @RequestParam String course,
            @RequestParam String gender) {

        List<Student> students = service.findByGenderAndCourse(gender, course);

        return ResponseEntity.ok(
                new ApiResponse<>("Filtered students fetched successfully", students)
        );
    }

    // UPDATE
    @PutMapping("/{rollNo}")
    public ResponseEntity<ApiResponse<Student>> update(
            @PathVariable int rollNo,
            @RequestBody Student student) {

        Student updated = service.updateStudent(rollNo, student);

        return ResponseEntity.ok(
                new ApiResponse<>("Student updated successfully", updated)
        );
    }

    // DELETE
    @DeleteMapping("/{rollNo}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable int rollNo) {

        String result = service.deleteStudent(rollNo);

        return ResponseEntity.ok(
                new ApiResponse<>("Student deleted successfully", result)
        );
    }

    // MOCK DATA
    @PostMapping("/addMock")
    public ResponseEntity<ApiResponse<List<Student>>> addMock() {

        List<Student> data = service.mockData();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Mock data inserted successfully", data));
    }
}