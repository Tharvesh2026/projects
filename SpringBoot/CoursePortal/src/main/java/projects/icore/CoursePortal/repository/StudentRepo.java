package projects.icore.CoursePortal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projects.icore.CoursePortal.entity.Student;

public interface StudentRepo extends JpaRepository<Student,Integer> {
}
