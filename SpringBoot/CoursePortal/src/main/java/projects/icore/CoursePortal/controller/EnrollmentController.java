package projects.icore.CoursePortal.controller;
import projects.icore.CoursePortal.entity.Enrollment;
import projects.icore.CoursePortal.services.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/v1/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public Enrollment registerCourse(
            @RequestParam Integer rollNo,
            @RequestParam String courseCode
    ) {
        return enrollmentService.registerCourse(rollNo, courseCode);
    }

    @GetMapping("/student/{rollNo}")
    public List<Enrollment> getCoursesByStudent(@PathVariable Integer rollNo) {
        return enrollmentService.getCoursesByStudent(rollNo);
    }

    @GetMapping("/course/{courseCode}")
    public List<Enrollment> getStudentsByCourse(@PathVariable String courseCode) {
        return enrollmentService.getStudentsByCourse(courseCode);
    }
}