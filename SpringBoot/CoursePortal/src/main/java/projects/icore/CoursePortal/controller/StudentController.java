package projects.icore.CoursePortal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import projects.icore.CoursePortal.dto.StudentRequest;
import projects.icore.CoursePortal.dto.StudentResponse;
import projects.icore.CoursePortal.services.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public StudentResponse createStudent(@RequestBody StudentRequest studentRequest) {

        log.info("POST /api/v1/students - Register request received. RollNo={}",
                studentRequest.rollNo());

        StudentResponse savedStudent = studentService.register(studentRequest);

        log.info("POST /api/v1/students - Student registered successfully. RollNo={}",
                savedStudent.rollNo());

        return savedStudent;
    }

    @GetMapping("/{rollNo}")
    public StudentResponse getStudent(@PathVariable Integer rollNo) {

        log.info("GET /api/v1/students/{} - Request received", rollNo);

        StudentResponse student = studentService.getById(rollNo);

        log.info("GET /api/v1/students/{} - Request completed", rollNo);

        return student;
    }

    @GetMapping
    public List<StudentResponse> getAllStudents() {

        log.info("GET /api/v1/students - Fetch all students request received");

        List<StudentResponse> students = studentService.getAll();

        log.info("GET /api/v1/students - Returned {} students", students.size());

        return students;
    }
}