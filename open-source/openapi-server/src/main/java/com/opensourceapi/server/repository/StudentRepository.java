package com.opensourceapi.server.repository;

import com.opensourceapi.server.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByMajorIgnoreCase(String major);
}
