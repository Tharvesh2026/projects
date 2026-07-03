package learning.springboot.JpaMapping.repository;

import learning.springboot.JpaMapping.model.Course;
import learning.springboot.JpaMapping.util.CourseStudentCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepo extends JpaRepository<Course, Integer> {
    Optional<Course> findByCode(String code);

    @Query(
            value = """
                SELECT 
                    c.code AS code,
                    c.title AS title,
                    COUNT(s.roll_no) AS studentCount
                FROM course c
                LEFT JOIN student s ON s.course_id = c.course_id
                GROUP BY c.course_id, c.code, c.title
                """,
            nativeQuery = true
    )
    List<CourseStudentCount> getCourseWiseStudentCount();

}
