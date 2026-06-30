package projects.icore.CoursePortal.controller;

import projects.icore.CoursePortal.entity.Course;
import projects.icore.CoursePortal.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

    @GetMapping("/{code}")
    public Course getCourse(@PathVariable String code) {
        return courseService.getCourse(code);
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }
}