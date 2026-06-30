package projects.icore.CoursePortal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import projects.icore.CoursePortal.dto.StudentProfileResponse;
import projects.icore.CoursePortal.entity.Student;
import projects.icore.CoursePortal.services.StudentProfileService;
import projects.icore.CoursePortal.services.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final StudentService studentService;
    private final StudentProfileService studentProfileService;

    @PostMapping
    public Student createStudent(@RequestBody Student student) {

        log.info("POST /api/v1/students - Register request received. RollNo={}",
                student.getRollNo());

        Student savedStudent = studentService.register(student);

        log.info("POST /api/v1/students - Student registered successfully. RollNo={}",
                savedStudent.getRollNo());

        return savedStudent;
    }

    @GetMapping("/{rollNo}")
    public Student getStudent(@PathVariable Integer rollNo) {

        log.info("GET /api/v1/students/{} - Request received", rollNo);

        Student student = studentService.getById(rollNo);

        log.info("GET /api/v1/students/{} - Request completed", rollNo);

        return student;
    }

    @GetMapping
    public List<Student> getAllStudents() {

        log.info("GET /api/v1/students - Fetch all students request received");

        List<Student> students = studentService.getAll();

        log.info("GET /api/v1/students - Returned {} students", students.size());

        return students;
    }

}