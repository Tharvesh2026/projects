package projects.icore.CoursePortal.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projects.icore.CoursePortal.dto.CourseRequest;
import projects.icore.CoursePortal.dto.CourseResponse;
import projects.icore.CoursePortal.entity.Course;
import projects.icore.CoursePortal.exception.ResourceNotFoundException;
import projects.icore.CoursePortal.mapper.CourseMapper;
import projects.icore.CoursePortal.repository.CourseRepo;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepo courseRepository;
    private final PortalStatsService portalStatsService;
    private final CourseMapper courseMapper;

    public CourseResponse createCourse(CourseRequest request) {
        log.info("Creating course. Code={}, Name={}", request.code(), request.name());

        log.info("Current total students = {}", portalStatsService.getTotalStudents());

        Course course = courseMapper.toEntity(request);
        Course savedCourse = courseRepository.save(course);

        log.info("Course created successfully. Code={}", savedCourse.getCode());

        return courseMapper.toResponse(savedCourse);
    }

    public CourseResponse getCourse(String code) {
        log.info("Fetching course with code={}", code);

        Course course = courseRepository.findById(code)
                .orElseThrow(() -> {
                    log.error("Course not found. Code={}", code);
                    return new ResourceNotFoundException("Course not found");
                });

        log.info("Course found. Code={}, Name={}",
                course.getCode(), course.getName());

        return courseMapper.toResponse(course);
    }

    public List<CourseResponse> getAllCourses() {
        log.info("Fetching all courses");

        List<Course> courses = courseRepository.findAll();

        log.info("Total courses found={}", courses.size());

        return courses.stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
    }

    public boolean exists(String code) {
        boolean exists = courseRepository.existsById(code);
        log.debug("Course exists check. Code={}, Exists={}",
                code, exists);
        return exists;
    }
}
