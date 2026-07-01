package com.example.Student.service;

import com.example.Student.model.Student;
import com.example.Student.repository.StudentRepoJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class StudentJPAService {

    @Autowired
    private StudentRepoJPA repo;

    // CREATE
    public Student addStudent(Student student) {
        return repo.save(student);
    }

    // READ ALL
    public List<Student> getAllStudents() {
        return repo.findAll();
    }

    // READ BY ID
    public Student getStudent(int rollNo) {
        return repo.findById(rollNo).orElse(null);
    }

    // UPDATE
    public Student updateStudent(int rollNo, Student updatedStudent) {
        Optional<Student> existing = repo.findById(rollNo);

        if (existing.isPresent()) {
            Student s = existing.get();
            s.setName(updatedStudent.getName());
            s.setGender(updatedStudent.getGender());
            s.setCourse(updatedStudent.getCourse());
            return repo.save(s);
        }
        return null;
    }

    // DELETE
    public String deleteStudent(int rollNo) {
        if (repo.existsById(rollNo)) {
            repo.deleteById(rollNo);
            return "Deleted Successfully";
        }
        return "Student Not Found";
    }

    public List<Student> findByTech(String tech){
        return repo.findByCourse(tech);
    }

    // MOCK DATA
    public List<Student> mockData() {

        List<Student> students = List.of(
                new Student("Alice", "F", 1, "Java"),
                new Student("Bob", "M", 2, "Python"),
                new Student("Charlie", "M", 3, "SpringBoot"),
                new Student("Diana", "F", 4, "DSA"),
                new Student("Ethan", "M", 5, "Java"),
                new Student("Fiona", "F", 6, "Python"),
                new Student("George", "M", 7, "SpringBoot"),
                new Student("Hannah", "F", 8, "DSA"),
                new Student("Ian", "M", 9, "Java"),
                new Student("Julia", "F", 10, "Python"),
                new Student("Kevin", "M", 11, "SpringBoot"),
                new Student("Lily", "F", 12, "DSA"),
                new Student("Michael", "M", 13, "Java"),
                new Student("Nora", "F", 14, "SpringBoot"),
                new Student("Oscar", "M", 15, "Python")
        );

        return repo.saveAll(students);
    }

    public List<Student> findByGenderAndCourse(String tech, String gender) {
        return repo.findByGenderAndCourse(tech,gender);
    }
}