package projects.icore.CoursePortal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import projects.icore.CoursePortal.entity.Course;
import projects.icore.CoursePortal.services.CourseService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
@Slf4j
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public Course createCourse(@RequestBody Course course) {

        log.info("POST /api/v1/courses - Create course request. Code={}",
                course.getCode());

        Course savedCourse = courseService.createCourse(course);

        log.info("POST /api/v1/courses - Course created successfully. Code={}",
                savedCourse.getCode());

        return savedCourse;
    }

    @GetMapping("/{code}")
    public Course getCourse(@PathVariable String code) {

        log.info("GET /api/v1/courses/{} - Request received", code);

        Course course = courseService.getCourse(code);

        log.info("GET /api/v1/courses/{} - Request completed", code);

        return course;
    }

    @GetMapping
    public List<Course> getAllCourses() {

        log.info("GET /api/v1/courses - Fetch all courses request received");

        List<Course> courses = courseService.getAllCourses();

        log.info("GET /api/v1/courses - Returned {} courses", courses.size());

        return courses;
    }
}