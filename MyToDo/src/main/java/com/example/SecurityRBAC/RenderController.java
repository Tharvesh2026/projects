package com.example.SecurityRBAC;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class RenderController implements ErrorController {
    @GetMapping("auth/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/test-error")
    public String test() {
        throw new RuntimeException("Hi Custom Exception working");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object path = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);

        model.addAttribute("status", status != null ? status : 500);
        model.addAttribute("message", (message != null && !message.toString().isEmpty())
                ? message : "Something went wrong");
        model.addAttribute("path", path);

        return "error";
    }
}

