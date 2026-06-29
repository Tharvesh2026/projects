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

    // MOCK DATA
    public Student mockData(){
        List<String> course = new ArrayList<>(Arrays.asList("Java","Spring Boot","MySQL"));
        Student mock = new Student("Alice","F",1, course);
        return repo.save(mock);
    }

}