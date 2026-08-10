package com.mydrive.app.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFound(NotFoundException ex, Model model, HttpServletResponse response) {
        response.setStatus(404);
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("status", 404);
        return "error/generic";
    }

    @ExceptionHandler(ForbiddenException.class)
    public String handleForbidden(ForbiddenException ex, Model model, HttpServletResponse response) {
        response.setStatus(403);
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("status", 403);
        return "error/generic";
    }
}
