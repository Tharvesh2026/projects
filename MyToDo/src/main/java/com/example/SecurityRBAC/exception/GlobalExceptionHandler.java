package com.example.SecurityRBAC.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(
            RuntimeException ex,
            Model model
    ) {

        log.error("Runtime exception occurred", ex);

        model.addAttribute(
                "message",
                ex.getMessage()
        );

        return "error";
    }
}