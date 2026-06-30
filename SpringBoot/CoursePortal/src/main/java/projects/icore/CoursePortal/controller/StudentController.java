package projects.icore.CoursePortal.controller;

import projects.icore.CoursePortal.entity.Student;
import projects.icore.CoursePortal.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.register(student);
    }

    @GetMapping("/{rollNo}")
    public Student getStudent(@PathVariable Integer rollNo) {
        return studentService.getById(rollNo);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAll();
    }
}