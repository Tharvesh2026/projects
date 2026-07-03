package learning.springboot.JpaMapping.controller;


import learning.springboot.JpaMapping.model.ApiResponse;
import learning.springboot.JpaMapping.model.Course;
import learning.springboot.JpaMapping.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v02/course")
public class CourseController {

    private final CourseService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Course>>> getCourse(){
        List<Course> courses = service.getAllCourse();
        return ResponseEntity.ok(new ApiResponse<>("Course Found",courses));
    }

    @GetMapping("/filtersBy")
    public ResponseEntity<ApiResponse<Course>> getByCode(@RequestParam("code") String title){
        Course course = service.getByCode(title);
        return ResponseEntity.ok(new ApiResponse<>("Course Found",course));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<List<Course>>> addCourse(){
        service.addCourse();
        return ResponseEntity.ok(new ApiResponse<>("Successfully Added",service.getAllCourse()));
    }

}
