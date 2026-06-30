package projects.icore.CoursePortal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import projects.icore.CoursePortal.dto.CourseRequest;
import projects.icore.CoursePortal.dto.CourseResponse;
import projects.icore.CoursePortal.services.CourseService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
@Slf4j
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public CourseResponse createCourse(@RequestBody CourseRequest courseRequest) {

        log.info("POST /api/v1/courses - Create course request. Code={}",
                courseRequest.code());

        CourseResponse savedCourse = courseService.createCourse(courseRequest);

        log.info("POST /api/v1/courses - Course created successfully. Code={}",
                savedCourse.code());

        return savedCourse;
    }

    @GetMapping("/{code}")
    public CourseResponse getCourse(@PathVariable String code) {

        log.info("GET /api/v1/courses/{} - Request received", code);

        CourseResponse course = courseService.getCourse(code);

        log.info("GET /api/v1/courses/{} - Request completed", code);

        return course;
    }

    @GetMapping
    public List<CourseResponse> getAllCourses() {

        log.info("GET /api/v1/courses - Fetch all courses request received");

        List<CourseResponse> courses = courseService.getAllCourses();

        log.info("GET /api/v1/courses - Returned {} courses", courses.size());

        return courses;
    }
}