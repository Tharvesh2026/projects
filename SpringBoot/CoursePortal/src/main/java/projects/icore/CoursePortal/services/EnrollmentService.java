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

        log.info("Enrollment request. RollNo={}, Course={}",
                rollNo, courseCode);

        if (!studentService.checkIdExists(rollNo)) {
            log.warn("Enrollment failed. Student not found. RollNo={}", rollNo);
            throw new RuntimeException("Student not found");
        }

        if (!courseService.exists(courseCode)) {
            log.warn("Enrollment failed. Course not found. Code={}", courseCode);
            throw new RuntimeException("Course not found");
        }

        if (enrollmentRepository.existsByRollNoAndCourseCode(rollNo, courseCode)) {
            log.warn("Enrollment failed. Student already enrolled. RollNo={}, Course={}",
                    rollNo, courseCode);
            throw new RuntimeException("Student already enrolled in this course");
        }

        Enrollment enrollment = Enrollment.builder()
                .rollNo(rollNo)
                .courseCode(courseCode)
                .build();

        Enrollment saved = enrollmentRepository.save(enrollment);

        log.info("Enrollment successful. RollNo={}, Course={}",
                rollNo, courseCode);

        return saved;
    }

    public List<Enrollment> getCoursesByStudent(Integer rollNo) {

        log.info("Fetching courses for student={}", rollNo);

        List<Enrollment> enrollments = enrollmentRepository.findByRollNo(rollNo);

        log.info("Student {} is enrolled in {} course(s)",
                rollNo, enrollments.size());

        return enrollments;
    }

    public List<Enrollment> getStudentsByCourse(String courseCode) {

        log.info("Fetching students for course={}", courseCode);

        List<Enrollment> enrollments = enrollmentRepository.findByCourseCode(courseCode);

        log.info("Course {} has {} student(s)",
                courseCode, enrollments.size());

        return enrollments;
    }
}
