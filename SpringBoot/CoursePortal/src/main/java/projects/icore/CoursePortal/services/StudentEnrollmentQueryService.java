package projects.icore.CoursePortal.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projects.icore.CoursePortal.repository.CourseRepo;
import projects.icore.CoursePortal.repository.EnrollementRepo;
import projects.icore.CoursePortal.repository.StudentRepo;

@Service
@RequiredArgsConstructor
public class StudentEnrollmentQueryService {

    private final StudentRepo studentRepo;
    private final CourseRepo courseRepo;
    private final EnrollementRepo enrollmentRepository;

    public boolean studentExists(Integer rollNo) {
        return studentRepo.existsById(rollNo);
    }

    public boolean courseExists(String courseCode) {
        return courseRepo.existsById(courseCode);
    }

    public long getEnrollmentCountByStudent(Integer rollNo) {
        return enrollmentRepository.findByRollNo(rollNo).size();
    }
}