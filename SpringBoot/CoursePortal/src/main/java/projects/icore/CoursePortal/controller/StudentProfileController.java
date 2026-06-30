package projects.icore.CoursePortal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import projects.icore.CoursePortal.dto.StudentProfileResponse;
import projects.icore.CoursePortal.services.StudentProfileService;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Slf4j
public class StudentProfileController {

    private final StudentProfileService studentProfileService;

    @GetMapping("/{rollNo}/profile")
    public StudentProfileResponse getStudentProfile(@PathVariable Integer rollNo) {

        log.info("GET /api/v1/students/{}/profile - Request received", rollNo);

        StudentProfileResponse response = studentProfileService.getProfile(rollNo);

        log.info("GET /api/v1/students/{}/profile - Request completed. EnrollmentCount={}",
                rollNo,
                response.enrollmentCount());

        return response;
    }
}