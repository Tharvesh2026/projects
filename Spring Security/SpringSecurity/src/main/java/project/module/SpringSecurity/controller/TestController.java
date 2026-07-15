package project.module.SpringSecurity.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import project.module.SpringSecurity.entity.Users;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/students")
public class TestController extends HttpServlet {


    @GetMapping
    public List<Users> getAll(HttpServletRequest req){
        System.out.println("Session Id: "+req.getSession().getId());
        return students;
    }

    @GetMapping("/getToken")
    public CsrfToken getCsrf(HttpServletRequest req){
        return (CsrfToken) req.getAttribute("_csrf");
    }

    @PostMapping
    public void addOne(@RequestBody Users student){
        students.add(student);
    }
}
