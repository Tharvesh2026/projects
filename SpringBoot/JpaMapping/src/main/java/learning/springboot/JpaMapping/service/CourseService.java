package learning.springboot.JpaMapping.service;

import learning.springboot.JpaMapping.exception.ResourceNotFoundException;
import learning.springboot.JpaMapping.model.Course;
import learning.springboot.JpaMapping.repository.CourseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepo repo;

    public void addCourse() {
        List<Course> courses = List.of(
            new Course("Java Programming", "java", "Sunil Diwakar", 3),
            new Course("Python for AI & ML", "python", "Ajay Manothra", 4),
            new Course("Spring Boot Microservices", "springboot", "Sharmila Banu", 7),
            new Course("Data Structures & Algorithms", "dsa", "Lily", 10),
            new Course("Full Stack Development (React + Spring Boot)", "fullstack", "Vikram Rao", 8),
            new Course("DevOps with Docker & Kubernetes", "devops", "Michael Chen", 6),
            new Course("AWS Cloud Practitioner", "aws", "Ananya Sharma", 5),
            new Course("Cyber Security Basics", "cybersecurity", "David John", 4),
            new Course("Data Science with Python", "datascience", "Neha Patel", 6),
            new Course("System Design Fundamentals", "systemdesign", "Karthik Subramanian", 12)
        );

        repo.saveAll(courses);
    }

    public List<Course> getAllCourse() {
        List<Course> courses = repo.findAll();
        if(courses.isEmpty()){
            throw new ResourceNotFoundException("Courses Not Available");
        }
        return courses;
    }

    public Course getByCode(String code){
        Optional<Course> course = repo.findByCode(code);
        if(!course.isPresent()){
            throw new ResourceNotFoundException("Course Not Found with code: "+code);
        }
        return course.get();
    }

}
