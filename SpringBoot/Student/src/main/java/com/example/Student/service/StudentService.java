package com.example.Student.service;

import com.example.Student.model.StudentModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class StudentService {

    private final List<StudentModel> students =
            new ArrayList<>(Arrays.asList(
                    new StudentModel(1, "jhon", "SDE"),
                    new StudentModel(2, "tom", "Networking"),
                    new StudentModel(3, "veronica", "Digital Marketing"),
                    new StudentModel(4, "raghavan", "Web Development"),
                    new StudentModel(5, "abilash", "UI/UX Designer")
            ));

    public List<StudentModel> getAllStudents() {
        if (students.isEmpty()) {
            log.warn("No students available");
            return new ArrayList<>();
        }
        log.info("Retrieved all students");
        return students;
    }

    public StudentModel getStudent(int id) {
        for (StudentModel student : students) {
            if (student.getRno() == id) {
                log.info("Retrieved student with id {}", id);
                return student;
            }
        }
        log.warn("Student not found with id {}", id);
        return null;
    }

    public boolean addStudent(StudentModel student) {
        boolean added = students.add(student);
        if (added) {
            log.info("Student added successfully: {}", student);
        }
        return added;
    }

    public boolean updateStudent(StudentModel student, int id) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getRno() == id) {
                students.set(i, student);
                log.info("Student updated successfully with id {}", id);
                return true;
            }
        }
        log.warn("Student not found for update with id {}", id);
        return false;
    }

    public boolean deleteStudent(int id) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getRno() == id) {
                StudentModel deletedStudent = students.get(i);
                students.remove(i);
                log.info("Deleted student: {}", deletedStudent);
                return true;
            }
        }
        log.warn("Student not found for deletion with id {}", id);
        return false;
    }
}