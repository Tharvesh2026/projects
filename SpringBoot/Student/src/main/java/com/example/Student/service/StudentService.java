package com.example.Student.service;

import com.example.Student.model.StudentModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class StudentService {

    private List<StudentModel> students = new ArrayList<>(
            Arrays.asList(
                    new StudentModel(1,"jhon","SDE"),
                    new StudentModel(2,"tom",""),
                    new StudentModel(3,"veronica",""),
                    new StudentModel(4,"raghavan",""),
                    new StudentModel(5,"abilash",""))
                    );
    public List<StudentModel> getAllStudents() {
        return  students;
    }

    public StudentModel getStudent(int id) throws NullPointerException{
        int index = 0;
        for(StudentModel sm :students){
            if (sm.getRno() == id) {
                index = id - 1;
                break;
            }
        }
        return students.get(index);
    }

    public boolean addStudent(StudentModel student){
        return students.add(student);
    }
}
