package com.example.Student.service;

import com.example.Student.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationQueryService {
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private CourseService courseService;

    public boolean checkCourseExist(String code){
        return courseService.getByCode(code)!=null;
    }

    public boolean checkStudentExist(String name){
        return studentRepo.findByName(name)!=null;
    }
}
