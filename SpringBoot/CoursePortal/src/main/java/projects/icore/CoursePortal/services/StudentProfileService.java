package projects.icore.CoursePortal.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projects.icore.CoursePortal.dto.CourseSummaryResponse;
import projects.icore.CoursePortal.dto.StudentProfileResponse;
import projects.icore.CoursePortal.entity.Course;
import projects.icore.CoursePortal.entity.Enrollment;
import projects.icore.CoursePortal.entity.Student;
import projects.icore.CoursePortal.exception.ResourceNotFoundException;
import projects.icore.CoursePortal.mapper.CourseMapper;
import projects.icore.CoursePortal.repository.CourseRepo;
import projects.icore.CoursePortal.repository.EnrollementRepo;
import projects.icore.CoursePortal.repository.StudentRepo;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentProfileService {

    private final StudentRepo studentRepo;
    private final CourseRepo courseRepo;
    private final EnrollementRepo enrollementRepo;
    private final CourseMapper courseMapper;

    public StudentProfileResponse getProfile(Integer rollNo) {
        log.info("Fetching student profile. RollNo={}", rollNo);

        Student student = studentRepo.findById(rollNo)
                .orElseThrow(() -> {
                    log.error("Student not found for profile. RollNo={}", rollNo);
                    return new ResourceNotFoundException("Student Not Found");
                });

        log.info("Student found. RollNo={}, Name={}",
                student.getRollNo(), student.getName());

        List<Enrollment> enrollments = enrollementRepo.findByRollNo(rollNo);

        log.info("Found {} enrollments for RollNo={}",
                enrollments.size(), rollNo);

        List<CourseSummaryResponse> courses = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            log.debug("Fetching course. CourseCode={}", enrollment.getCourseCode());
            Course course = courseRepo.findById(enrollment.getCourseCode())
                    .orElseThrow(() -> new ResourceNotFoundException("Course Not Found: " + enrollment.getCourseCode()));
            courses.add(courseMapper.toSummary(course));
        }

        log.info("Student profile prepared. RollNo={}, TotalCourses={}",
                rollNo, courses.size());

        return new StudentProfileResponse(
                student.getRollNo(),
                student.getName(),
                student.getPhone(),
                courses,
                courses.size()
        );
    }
}