package com.example.Student.service;

import com.example.Student.exception.ResourceNotFoundException;
import com.example.Student.model.Student;
import com.example.Student.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepo repo;
    @Autowired
    private ValidationQueryService queryService;

    // CREATE
    public Student addStudent(Student student) {
        return repo.save(student);
    }

    // READ ALL
    public List<Student> getAllStudents() {
        return repo.findAll();
    }

    // READ BY ID
    public Student getStudent(int rollNo) {
        return repo.findById(rollNo).orElseThrow(()->new ResourceNotFoundException("Student not found with RollNo: "+rollNo));
    }

    // UPDATE
    public Student updateStudent(int rollNo, Student updatedStudent) throws ResourceNotFoundException {
        Student s = repo.findById(rollNo).orElseThrow(()->new ResourceNotFoundException("Can't Update, Student not found with rollNo: " + rollNo));

        s.setName(updatedStudent.getName());
        s.setGender(updatedStudent.getGender());
        s.setCourse(updatedStudent.getCourse());
        return repo.save(s);
    }

    // DELETE
    public void deleteStudent(int rollNo) {

        if (!repo.existsById(rollNo)) {
            throw new ResourceNotFoundException("Student not found with rollNo: " + rollNo);
        }

        repo.deleteById(rollNo);
    }

    public List<Student> findByTech(String tech){
        if(!queryService.checkCourseExist(tech)){
            throw new ResourceNotFoundException("Course Not Found");
        }
        return repo.findByCourse(tech);
    }

    // MOCK DATA
    public List<Student> mockData() {

        List<Student> students = List.of(
                new Student("Alice", "F", 1, "datascience"),
                new Student("Bob", "M", 2, "java"),
                new Student("Charlie", "M", 3, "aws"),
                new Student("Diana", "F", 4, "python"),
                new Student("Ethan", "M", 5, "fullstack"),
                new Student("Fiona", "F", 6, "cybersecurity"),
                new Student("George", "M", 7, "springboot"),
                new Student("Hannah", "F", 8, "dsa"),
                new Student("Ian", "M", 9, "devops"),
                new Student("Julia", "F", 10, "systemdesign"),
                new Student("Kevin", "M", 11, "python"),
                new Student("Lily", "F", 12, "aws"),
                new Student("Michael", "M", 13, "java"),
                new Student("Nora", "F", 14, "fullstack"),
                new Student("Oscar", "M", 15, "datascience"),
                new Student("Priya", "F", 16, "springboot"),
                new Student("Rahul", "M", 17, "cybersecurity"),
                new Student("Sneha", "F", 18, "devops"),
                new Student("Arjun", "M", 19, "dsa"),
                new Student("Meera", "F", 20, "systemdesign"),
                new Student("Vikram", "M", 21, "python"),
                new Student("Ananya", "F", 22, "java"),
                new Student("Karthik", "M", 23, "aws"),
                new Student("Divya", "F", 24, "springboot"),
                new Student("Rohit", "M", 25, "fullstack"),
                new Student("Pooja", "F", 26, "datascience"),
                new Student("Sanjay", "M", 27, "devops"),
                new Student("Aishwarya", "F", 28, "cybersecurity"),
                new Student("Manoj", "M", 29, "dsa"),
                new Student("Keerthana", "F", 30, "python")
        );

        return repo.saveAll(students);
    }

    public void deleteMock(boolean state){
        if(!state){
            throw new ResourceNotFoundException("No Data Found to delete");
        }
        repo.deleteAll();
    }

    public List<Student> findByGenderAndCourse(String gender, String tech) {
        List<Student> students = repo.findByGenderAndCourse(gender,tech);
        if(students.isEmpty()) throw new ResourceNotFoundException("Not Student Satisfied the filter condition");
        return students;
    }
}