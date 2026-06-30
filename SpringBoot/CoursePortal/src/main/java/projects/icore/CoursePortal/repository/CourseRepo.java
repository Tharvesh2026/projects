package projects.icore.CoursePortal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.icore.CoursePortal.entity.Course;

public interface CourseRepo extends JpaRepository<Course, String> {
}
