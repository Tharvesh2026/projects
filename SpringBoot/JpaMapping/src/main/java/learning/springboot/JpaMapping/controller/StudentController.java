package learning.springboot.JpaMapping.controller;

import learning.springboot.JpaMapping.dto.StudentResponseDTO;
import learning.springboot.JpaMapping.model.ApiResponse;
import learning.springboot.JpaMapping.model.Student;
import learning.springboot.JpaMapping.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v02/students")
@CrossOrigin("*")
public class StudentController {

    private final StudentService service;

    // GET ALL
    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponseDTO>>> getAll() {

        List<StudentResponseDTO> students = service.getAllStudents();

        return ResponseEntity.ok(
                new ApiResponse<>("Students fetched successfully", students)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> getOne(@PathVariable("id") int id) {

        StudentResponseDTO student = service.getOne(id);

        return ResponseEntity.ok(
                new ApiResponse<>("Students fetched successfully", student)
        );
    }



}