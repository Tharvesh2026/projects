package projects.icore.CoursePortal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import projects.icore.CoursePortal.entity.Enrollment;
import projects.icore.CoursePortal.services.EnrollmentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enrollments")
@RequiredArgsConstructor
@Slf4j
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public Enrollment registerCourse(
            @RequestParam Integer rollNo,
            @RequestParam String courseCode) {

        log.info("POST /api/v1/enrollments - Enrollment request. RollNo={}, Course={}",
                rollNo, courseCode);

        Enrollment enrollment = enrollmentService.registerCourse(rollNo, courseCode);

        log.info("POST /api/v1/enrollments - Enrollment successful. RollNo={}, Course={}",
                rollNo, courseCode);

        return enrollment;
    }

    @GetMapping("/student/{rollNo}")
    public List<Enrollment> getCoursesByStudent(@PathVariable Integer rollNo) {

        log.info("GET /api/v1/enrollments/student/{} - Request received", rollNo);

        List<Enrollment> enrollments = enrollmentService.getCoursesByStudent(rollNo);

        log.info("GET /api/v1/enrollments/student/{} - Returned {} enrollment(s)",
                rollNo, enrollments.size());

        return enrollments;
    }

    @GetMapping("/course/{courseCode}")
    public List<Enrollment> getStudentsByCourse(@PathVariable String courseCode) {

        log.info("GET /api/v1/enrollments/course/{} - Request received", courseCode);

        List<Enrollment> enrollments = enrollmentService.getStudentsByCourse(courseCode);

        log.info("GET /api/v1/enrollments/course/{} - Returned {} enrollment(s)",
                courseCode, enrollments.size());

        return enrollments;
    }
}