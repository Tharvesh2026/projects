package com.example.Student.controller;

import com.example.Student.model.ApiResponse;
import com.example.Student.model.Student;
import com.example.Student.service.StudentJPAService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v01/students")
@CrossOrigin("*")
public class StudentJPAController {

    private final StudentJPAService service;

    // CREATE
    @PostMapping
    public ResponseEntity<ApiResponse<Student>> addStudent(@RequestBody Student student) {

        Student saved = service.addStudent(student);

        if (saved == null) {
            return new ResponseEntity<>(
                    new ApiResponse<>("Student creation failed", null),
                    HttpStatus.BAD_REQUEST
            );
        }

        return new ResponseEntity<>(
                new ApiResponse<>("Student added successfully", saved),
                HttpStatus.CREATED
        );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<ApiResponse<List<Student>>> getAll() {

        List<Student> students = service.getAllStudents();

        if (students == null || students.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse<>("No students found", students),
                    HttpStatus.NO_CONTENT
            );
        }

        return new ResponseEntity<>(
                new ApiResponse<>("Students fetched successfully", students),
                HttpStatus.OK
        );
    }

    // GET ONE
    @GetMapping("/{rollNo}")
    public ResponseEntity<ApiResponse<Student>> getOne(@PathVariable int rollNo) {

        Student student = service.getStudent(rollNo);

        if (student == null) {
            return new ResponseEntity<>(
                    new ApiResponse<>("Student not found with rollNo: " + rollNo, null),
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                new ApiResponse<>("Student fetched successfully", student),
                HttpStatus.OK
        );
    }

    @PostMapping("/clearAll")
    public ResponseEntity<ApiResponse<String>> deleteAll(@RequestParam("confirm") boolean state){
        String status = service.deleteMock(state);

        if (status == null) {
            return new ResponseEntity<>(
                    new ApiResponse<>("Request Cancel By User", null),
                    HttpStatus.NO_CONTENT
            );
        }

        return new ResponseEntity<>(
                new ApiResponse<>("Student data cleared", null),
                HttpStatus.OK
        );
    }

    // FILTER BY COURSE
    @GetMapping("/filtersBy")
    public ResponseEntity<ApiResponse<List<Student>>> findByCourse(@RequestParam("course") String tech) {

        List<Student> students = service.findByTech(tech);

        if (students == null || students.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse<>("No students found for course: " + tech, students),
                    HttpStatus.NO_CONTENT
            );
        }

        return new ResponseEntity<>(
                new ApiResponse<>("Filtered students fetched successfully", students),
                HttpStatus.OK
        );
    }

    // FILTER BY GENDER + COURSE
    @PostMapping("/filtersBy")
    public ResponseEntity<ApiResponse<List<Student>>> findByGenderandCourse(
            @RequestParam("course") String tech,
            @RequestParam("gender") String gender) {

        List<Student> students = service.findByGenderAndCourse(gender, tech);

        if (students == null || students.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse<>("No students found for given filters", students),
                    HttpStatus.NO_CONTENT
            );
        }

        return new ResponseEntity<>(
                new ApiResponse<>("Filtered students fetched successfully", students),
                HttpStatus.OK
        );
    }

    // UPDATE
    @PutMapping("/{rollNo}")
    public ResponseEntity<ApiResponse<Student>> update(@PathVariable int rollNo,
                                                       @RequestBody Student student) {

        Student updated = service.updateStudent(rollNo, student);

        if (updated == null) {
            return new ResponseEntity<>(
                    new ApiResponse<>("Student not found for update", null),
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                new ApiResponse<>("Student updated successfully", updated),
                HttpStatus.OK
        );
    }

    // DELETE
    @DeleteMapping("/{rollNo}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable int rollNo) {

        String result = service.deleteStudent(rollNo);

        if (result == null || result.isBlank()) {
            return new ResponseEntity<>(
                    new ApiResponse<>("Student not found for deletion", null),
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                new ApiResponse<>("Student deleted successfully", result),
                HttpStatus.OK
        );
    }

    // MOCK DATA
    @PostMapping("/addMock")
    public ResponseEntity<ApiResponse<List<Student>>> addMock() {

        List<Student> data = service.mockData();

        if (data == null || data.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse<>("Mock data not created", data),
                    HttpStatus.NO_CONTENT
            );
        }

        return new ResponseEntity<>(
                new ApiResponse<>("Mock data inserted successfully", data),
                HttpStatus.CREATED
        );
    }
}