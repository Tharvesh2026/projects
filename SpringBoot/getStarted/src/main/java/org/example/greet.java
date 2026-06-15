package org.example;

import lombok.Getter;

@Getter
public class greet implements printer{
    private int num;
    public void hello(){
        System.out.println("Build process Initiated");
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public void print() {
        System.out.println("Build Finished");
    }
}
