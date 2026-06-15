package org.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App 
{
    public static void applicationContextMain()
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
        greet g = (greet) context.getBean("greets");
        g.hello();
        g.setNum(20);
        System.out.println("num:"+g.getNum());
        g.print();
    }

    public static void LambokMain() {
        ProjLambok lambok = new ProjLambok();
        lambok.setName("Lambok Project");
        lambok.setNum(18);
        System.out.println(lambok.toString());
        lambok.print();
    }

    public static void main(String[] args) {
        applicationContextMain();
        LambokMain();
    }

}
