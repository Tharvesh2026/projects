package projects.icore.CoursePortal.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projects.icore.CoursePortal.repository.CourseRepo;
import projects.icore.CoursePortal.repository.StudentRepo;

@Service
@RequiredArgsConstructor
public class PortalStatsService {

    private final StudentRepo studentRepository;
    private final CourseRepo courseRepository;

    public long getTotalStudents() {
        return studentRepository.count();
    }

    public long getTotalCourses() {
        return courseRepository.count();
    }
}