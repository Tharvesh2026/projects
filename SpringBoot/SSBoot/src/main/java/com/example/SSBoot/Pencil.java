package com.example.SSBoot;

import org.springframework.stereotype.Component;

@Component
public class Pencil {
    Pencil(){
        System.out.println("Having Pencil");
    }

    void write(){
        System.out.println("Exam With Pencil");
    }
}
