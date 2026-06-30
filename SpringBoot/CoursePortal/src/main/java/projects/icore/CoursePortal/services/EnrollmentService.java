package projects.icore.CoursePortal.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import projects.icore.CoursePortal.entity.Enrollment;
import projects.icore.CoursePortal.repository.EnrollementRepo;
import projects.icore.CoursePortal.repository.StudentRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmentService {
    private final EnrollementRepo enrollmentRepository;
    private final StudentService studentService;
    private final CourseService courseService;

    public Enrollment registerCourse(Integer rollNo, String courseCode) {

        if (!studentService.checkIdExists(rollNo)) {
            throw new RuntimeException("Student not found");
        }

        if (!courseService.exists(courseCode)) {
            throw new RuntimeException("Course not found");
        }

        if (enrollmentRepository.existsByRollNoAndCourseCode(rollNo, courseCode)) {
            throw new RuntimeException("Student already enrolled in this course");
        }

        Enrollment enrollment = Enrollment.builder()
                .rollNo(rollNo)
                .courseCode(courseCode)
                .build();

        return enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getCoursesByStudent(Integer rollNo) {
        return enrollmentRepository.findByRollNo(rollNo);
    }

    public List<Enrollment> getStudentsByCourse(String courseCode) {
        return enrollmentRepository.findByCourseCode(courseCode);
    }
}
