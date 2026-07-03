package learning.springboot.JpaMapping.exception;

public class ResourceNotFoundException extends ApplicationException{
    public ResourceNotFoundException(String message){
        super(message, "RESOURCE_NOT_FOUND");
    }
}
