package com.example.SSBoot.StereotypeAnotation;
import org.springframework.stereotype.Component;

@Component
public class Pen {
    public Pen() {
        System.out.println("Pen Created");
    }

    public void write(){
        System.out.println("Written by Pen");
    }
}
