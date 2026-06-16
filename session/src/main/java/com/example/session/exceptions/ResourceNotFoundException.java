package com.example.session.exceptions;

public class ResourceNotFoundException extends ApplicationException{
    public ResourceNotFoundException(String msg){
        super(msg,404);
    }
    
}
