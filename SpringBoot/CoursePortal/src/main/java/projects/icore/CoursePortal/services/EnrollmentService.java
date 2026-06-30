package projects.icore.CoursePortal.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projects.icore.CoursePortal.dto.EnrollmentRequest;
import projects.icore.CoursePortal.dto.EnrollmentResponse;
import projects.icore.CoursePortal.entity.Enrollment;
import projects.icore.CoursePortal.exception.DuplicateEnrollmentException;
import projects.icore.CoursePortal.exception.ResourceNotFoundException;
import projects.icore.CoursePortal.mapper.EnrollmentMapper;
import projects.icore.CoursePortal.repository.EnrollementRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmentService {
    private final EnrollementRepo enrollmentRepository;
    private final StudentEnrollmentQueryService queryService;
    private final EnrollmentMapper enrollmentMapper;

    public EnrollmentResponse registerCourse(EnrollmentRequest request) {
        Integer rollNo = request.rollNo();
        String courseCode = request.courseCode();

        log.info("Enrollment request. RollNo={}, Course={}",
                rollNo, courseCode);

        if (!queryService.studentExists(rollNo)) {
            log.warn("Enrollment failed. Student not found. RollNo={}", rollNo);
            throw new ResourceNotFoundException("Student not found");
        }

        if (!queryService.courseExists(courseCode)) {
            log.warn("Enrollment failed. Course not found. Code={}", courseCode);
            throw new ResourceNotFoundException("Course not found");
        }

        if (queryService.alreadyEnrolled(rollNo, courseCode)) {
            log.warn("Enrollment failed. Student already enrolled. RollNo={}, Course={}",
                    rollNo, courseCode);
            throw new DuplicateEnrollmentException("Student already enrolled in this course");
        }

        Enrollment enrollment = Enrollment.builder()
                .rollNo(rollNo)
                .courseCode(courseCode)
                .build();

        Enrollment saved = enrollmentRepository.save(enrollment);

        log.info("Enrollment successful. RollNo={}, Course={}",
                rollNo, courseCode);

        return enrollmentMapper.toResponse(saved);
    }

    public List<EnrollmentResponse> getCoursesByStudent(Integer rollNo) {
        log.info("Fetching courses for student={}", rollNo);

        if (!queryService.studentExists(rollNo)) {
            throw new ResourceNotFoundException("Student not found");
        }

        List<Enrollment> enrollments = enrollmentRepository.findByRollNo(rollNo);

        log.info("Student {} is enrolled in {} course(s)",
                rollNo, enrollments.size());

        return enrollments.stream()
                .map(enrollmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<EnrollmentResponse> getStudentsByCourse(String courseCode) {
        log.info("Fetching students for course={}", courseCode);

        if (!queryService.courseExists(courseCode)) {
            throw new ResourceNotFoundException("Course not found");
        }

        List<Enrollment> enrollments = enrollmentRepository.findByCourseCode(courseCode);

        log.info("Course {} has {} student(s)",
                courseCode, enrollments.size());

        return enrollments.stream()
                .map(enrollmentMapper::toResponse)
                .collect(Collectors.toList());
    }
}
