package com.example.Student.repository;

import com.example.Student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepoJPA extends JpaRepository<Student,Integer> {
}
