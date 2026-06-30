package projects.icore.CoursePortal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import projects.icore.CoursePortal.dto.EnrollmentRequest;
import projects.icore.CoursePortal.dto.EnrollmentResponse;
import projects.icore.CoursePortal.services.EnrollmentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enrollments")
@RequiredArgsConstructor
@Slf4j
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public EnrollmentResponse registerCourse(@RequestBody EnrollmentRequest enrollmentRequest) {

        log.info("POST /api/v1/enrollments - Enrollment request. RollNo={}, Course={}",
                enrollmentRequest.rollNo(), enrollmentRequest.courseCode());

        EnrollmentResponse enrollment = enrollmentService.registerCourse(enrollmentRequest);

        log.info("POST /api/v1/enrollments - Enrollment successful. RollNo={}, Course={}",
                enrollmentRequest.rollNo(), enrollmentRequest.courseCode());

        return enrollment;
    }

    @GetMapping("/student/{rollNo}")
    public List<EnrollmentResponse> getCoursesByStudent(@PathVariable Integer rollNo) {

        log.info("GET /api/v1/enrollments/student/{} - Request received", rollNo);

        List<EnrollmentResponse> enrollments = enrollmentService.getCoursesByStudent(rollNo);

        log.info("GET /api/v1/enrollments/student/{} - Returned {} enrollment(s)",
                rollNo, enrollments.size());

        return enrollments;
    }

    @GetMapping("/course/{courseCode}")
    public List<EnrollmentResponse> getStudentsByCourse(@PathVariable String courseCode) {

        log.info("GET /api/v1/enrollments/course/{} - Request received", courseCode);

        List<EnrollmentResponse> enrollments = enrollmentService.getStudentsByCourse(courseCode);

        log.info("GET /api/v1/enrollments/course/{} - Returned {} enrollment(s)",
                courseCode, enrollments.size());

        return enrollments;
    }
}