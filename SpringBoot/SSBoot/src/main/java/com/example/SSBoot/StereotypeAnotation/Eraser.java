package com.example.SSBoot.StereotypeAnotation;

import org.springframework.stereotype.Component;

@Component
public class Eraser {
    public Eraser() {
        System.out.println("Line Erased");
    }
}
