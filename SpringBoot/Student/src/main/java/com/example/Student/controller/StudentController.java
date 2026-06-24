package com.example.Student.controller;

import com.example.Student.model.StudentModel;
import com.example.Student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/get/students")
    public List<StudentModel> getStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping("/get/student/id={rno}")
    public StudentModel getStudent(@PathVariable("rno") int id){
        return studentService.getStudent(id);
    }

    @PostMapping("/add/student")
    public String addStudent(@RequestBody StudentModel student){
        String status = "Failed";
        if(studentService.addStudent(student)){
            status = "Success";
        }
        return status;
    }
}
