package learning.springboot.JpaMapping.controller;

import learning.springboot.JpaMapping.dto.CourseRequestDTO;
import learning.springboot.JpaMapping.dto.CourseResponseDTO;
import learning.springboot.JpaMapping.model.ApiResponse;
import learning.springboot.JpaMapping.service.CourseService;
import learning.springboot.JpaMapping.util.CourseStudentCount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v2/courses")
public class CourseController {

    private final CourseService service;

    @PostMapping("/mock")
    public ResponseEntity<ApiResponse<String>> addMock() {
        service.addMock();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Mock courses inserted successfully", null));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CourseResponseDTO>> addCourse(
            @RequestBody CourseRequestDTO req
    ) {
        CourseResponseDTO course = service.addCourse(req);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Course created successfully", course));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResponseDTO>>> getAll() {
        List<CourseResponseDTO> courses = service.getAll();

        return ResponseEntity.ok(
                new ApiResponse<>("Courses fetched successfully", courses)
        );
    }

    @GetMapping("/{code}")
    public ResponseEntity<ApiResponse<CourseResponseDTO>> getOne(
            @PathVariable String code
    ) {
        CourseResponseDTO course = service.getOne(code);

        return ResponseEntity.ok(
                new ApiResponse<>("Course fetched successfully with code: " + code, course)
        );
    }

    @PutMapping("/{code}")
    public ResponseEntity<ApiResponse<CourseResponseDTO>> updateCourse(
            @PathVariable String code,
            @RequestBody CourseRequestDTO req
    ) {
        CourseResponseDTO course = service.updateOne(code, req);

        return ResponseEntity.ok(
                new ApiResponse<>("Course updated successfully", course)
        );
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<ApiResponse<String>> deleteCourse(
            @PathVariable String code
    ) {
        service.deleteOne(code);

        return ResponseEntity.ok(
                new ApiResponse<>("Course deleted successfully. Enrolled students are now unassigned.", code)
        );
    }

    @GetMapping("/summary/student-count")
    public ResponseEntity<ApiResponse<List<CourseStudentCount>>> getSummary() {
        List<CourseStudentCount> summary = service.getSummary();

        return ResponseEntity.ok(
                new ApiResponse<>("Course-wise student count summary", summary)
        );
    }

    @GetMapping("/{code}/student-count")
    public ResponseEntity<ApiResponse<Long>> getStudentCountByCourse(
            @PathVariable String code
    ) {
        long count = service.getStudentCountByCourse(code);

        return ResponseEntity.ok(
                new ApiResponse<>("Student count fetched successfully for course: " + code, count)
        );
    }
}