package learning.springboot.JpaMapping.repository;

import learning.springboot.JpaMapping.model.Course;
import learning.springboot.JpaMapping.util.CourseStudentCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepo extends JpaRepository<Course, Integer> {

    Optional<Course> findByCode(String code);

    boolean existsByCode(String code);

    @Query(
            value = """
                    SELECT COUNT(s.roll_no)
                    FROM student s
                    JOIN course c ON s.course_id = c.course_id
                    WHERE c.code = :code
                    """,
            nativeQuery = true
    )
    long countStudentsByCourseCode(String code);

    @Modifying
    @Query(
            value = """
                    UPDATE student
                    SET course_id = NULL
                    WHERE course_id = :courseId
                    """,
            nativeQuery = true
    )
    int removeCourseFromStudents(Integer courseId);

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