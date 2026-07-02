package com.example.Student.repository;

import com.example.Student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student,Integer> {
    List<Student> findByCourse(String course);
    Student findByName(String name);

    @Query(value = "SELECT * FROM Student WHERE gender = :gender AND course = :course",
            nativeQuery = true)
    List<Student> findByGenderAndCourse
            (@Param("gender") String gender, @Param("course") String course);
}
