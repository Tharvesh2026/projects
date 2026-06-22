package com.example.SSBoot;

import com.example.SSBoot.StereotypeAnotation.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SsBootApplication {
	static void main(String[] args) {
		SpringApplication.run(SsBootApplication.class,args);
	}


	public static void StereoMain(String[] args) {
		ApplicationContext context = SpringApplication.run(SsBootApplication.class, args);

		Student stu = context.getBean(Student.class);
		stu.setAge(21);
		stu.writeExam();

	}

}
