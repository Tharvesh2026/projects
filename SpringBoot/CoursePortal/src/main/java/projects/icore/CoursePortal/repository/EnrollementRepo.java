package projects.icore.CoursePortal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.icore.CoursePortal.entity.Enrollment;

import java.util.List;
import java.util.Optional;

public interface EnrollementRepo extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByRollNo(Integer rollNo);

    List<Enrollment> findByCourseCode(String courseCode);

    Optional<Enrollment> findByRollNoAndCourseCode(Integer rollNo, String courseCode);

    boolean existsByRollNoAndCourseCode(Integer rollNo, String courseCode);
}