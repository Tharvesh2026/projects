package com.example.SSBoot.StereotypeAnotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class Student {
    int age;
    private Eraser eraser;
    private Pencil pencil;
    @Autowired
    private Pen pen;

    public void setAge(int age) {
        this.age = age;
    }

    public Student(Eraser eraser) {
        this.eraser = eraser;
    }

    @Autowired
    public void setPencil(Pencil pencil) {
        this.pencil = pencil;
    }

    public void show(){
        System.out.println("I'm Student");
    }
    public void writeExam(){
        pen.write();
        pencil.write();
    }

}
