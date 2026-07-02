package com.example.Student.exception;

public class BusinessException extends ApplicationException{
    public BusinessException(String message){
        super(message, "LOGIC_ERROR");
    }
}
