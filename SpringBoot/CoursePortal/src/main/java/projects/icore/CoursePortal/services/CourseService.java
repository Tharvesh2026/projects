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
    private final StudentService studentService;

    public Course createCourse(Course course) {
        log.info("Total No of Students:  {}", portalStatsService.getTotalStudents());
        return courseRepository.save(course);
    }

    public Course getCourse(String code) {
        return courseRepository.findById(code)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public boolean exists(String code) {
        return courseRepository.existsById(code);
    }

    public boolean canAllowCourseForStudent(Integer rollNo) {
        return studentService.checkIdExists(rollNo);
    }
}
