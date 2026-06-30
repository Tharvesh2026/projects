package projects.icore.CoursePortal.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projects.icore.CoursePortal.entity.Course;
import projects.icore.CoursePortal.repository.CourseRepo;


import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepo courseRepository;
    private final PortalStatsService portalStatsService;

    public Course createCourse(Course course) {

        log.info("Creating course. Code={}, Name={}", course.getCode(), course.getName());

        log.info("Current total students = {}", portalStatsService.getTotalStudents());

        Course savedCourse = courseRepository.save(course);

        log.info("Course created successfully. Code={}", savedCourse.getCode());

        return savedCourse;
    }

    public Course getCourse(String code) {

        log.info("Fetching course with code={}", code);

        Course course = courseRepository.findById(code)
                .orElseThrow(() -> {
                    log.error("Course not found. Code={}", code);
                    return new RuntimeException("Course not found");
                });

        log.info("Course found. Code={}, Name={}",
                course.getCode(), course.getName());

        return course;
    }

    public List<Course> getAllCourses() {

        log.info("Fetching all courses");

        List<Course> courses = courseRepository.findAll();

        log.info("Total courses found={}", courses.size());

        return courses;
    }

    public boolean exists(String code) {

        boolean exists = courseRepository.existsById(code);

        log.debug("Course exists check. Code={}, Exists={}",
                code, exists);

        return exists;
    }

}
