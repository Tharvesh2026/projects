package com.opensourceapi.server.controller;

import com.opensourceapi.server.dto.StudentRequest;
import com.opensourceapi.server.entity.Student;
import com.opensourceapi.server.exception.ApiException;
import com.opensourceapi.server.repository.StudentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Tag(name = "Students", description = "Mock student management endpoints — great for LMS apps")
public class StudentController {

    private final StudentRepository studentRepository;

    @GetMapping
    @Operation(summary = "List all students, optionally filtered by major (public)")
    public List<Student> all(@RequestParam(required = false) String major) {
        if (major != null && !major.isBlank()) {
            return studentRepository.findByMajorIgnoreCase(major);
        }
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a student by id (public)")
    public Student getOne(@PathVariable Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ApiException("Student not found", HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create a student (requires auth)")
    public ResponseEntity<Student> create(@Valid @RequestBody StudentRequest request) {
        Student student = Student.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .enrollmentDate(request.getEnrollmentDate())
                .major(request.getMajor())
                .gpa(request.getGpa())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(studentRepository.save(student));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update a student (requires auth)")
    public Student update(@PathVariable Long id, @Valid @RequestBody StudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ApiException("Student not found", HttpStatus.NOT_FOUND));
        
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setEnrollmentDate(request.getEnrollmentDate());
        student.setMajor(request.getMajor());
        student.setGpa(request.getGpa());

        return studentRepository.save(student);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete a student (requires auth)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ApiException("Student not found", HttpStatus.NOT_FOUND));
        studentRepository.delete(student);
        return ResponseEntity.noContent().build();
    }
}
