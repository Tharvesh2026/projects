package projects.icore.CoursePortal.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projects.icore.CoursePortal.dto.StudentProfileResponse;
import projects.icore.CoursePortal.entity.Course;
import projects.icore.CoursePortal.entity.Enrollment;
import projects.icore.CoursePortal.entity.Student;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentProfileService {

    private final StudentService studentService;
    private final EnrollmentService enrollmentService;
    private final CourseService courseService;

    public StudentProfileResponse getProfile(Integer rollNo) {

        log.info("Fetching student profile. RollNo={}", rollNo);

        Student student = studentService.getById(rollNo);

        log.info("Student found. RollNo={}, Name={}",
                student.getRollNo(), student.getName());

        List<Enrollment> enrollments = enrollmentService.getCoursesByStudent(rollNo);

        log.info("Found {} enrollments for RollNo={}",
                enrollments.size(), rollNo);

        List<Course> courses = enrollments.stream()
                .map(enrollment -> {
                    log.debug("Fetching course. CourseCode={}",
                            enrollment.getCourseCode());
                    return courseService.getCourse(enrollment.getCourseCode());
                })
                .toList();

        log.info("Student profile prepared. RollNo={}, TotalCourses={}",
                rollNo, courses.size());

        return new StudentProfileResponse(
                student,
                courses,
                courses.size()
        );
    }
}