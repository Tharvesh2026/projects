package com.example.Student.controller;

import com.example.Student.model.StudentModel;
import com.example.Student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<StudentModel> getStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{rno}")
    public StudentModel getStudent(@PathVariable int rno) {
        return studentService.getStudent(rno);
    }

    @PostMapping
    public String addStudent(@RequestBody StudentModel student) {

        return studentService.addStudent(student)
                ? "Success"
                : "Failed";
    }

    @PutMapping("/{rno}")
    public String updateStudent(
            @RequestBody StudentModel student,
            @PathVariable int rno) {

        return studentService.updateStudent(student, rno)
                ? "Success"
                : "Failed";
    }

    @DeleteMapping("/{rno}")
    public String deleteStudent(@PathVariable int rno) {

        return studentService.deleteStudent(rno)
                ? "Success"
                : "Failed";
    }
}