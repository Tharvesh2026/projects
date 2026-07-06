package project.module.SpringSecurity.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import project.module.SpringSecurity.entity.Student;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/students")
public class TestController extends HttpServlet {
    List<Student> students = new ArrayList<>(List.of(
            new Student("Arthi", 1, "CSE"),
            new Student("Kamal", 2, "CSE")
    ));


    @GetMapping
    public List<Student> getAll(){
        return students;
    }

    @GetMapping("/getToken")
    public CsrfToken getCsrf(HttpServletRequest req){
        return (CsrfToken) req.getAttribute("_csrf");
    }

    @PostMapping
    public void addOne(@RequestBody Student student){
        students.add(student);
    }
}
