package learning.springboot.JpaMapping.repository;

import learning.springboot.JpaMapping.dto.StudentResponseDTO;
import learning.springboot.JpaMapping.model.Course;
import learning.springboot.JpaMapping.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepo extends JpaRepository<Student, Integer> {
}
