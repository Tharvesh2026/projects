package org.example;

import lombok.Data;

@Data
public class ProjLambok implements printer{
    private int num;
    private String name;

    ProjLambok(){
        System.out.println("Lambok Initiated");
    }

    @Override
    public void print() {
        System.out.println("Project lambok Completed");
    }
}
