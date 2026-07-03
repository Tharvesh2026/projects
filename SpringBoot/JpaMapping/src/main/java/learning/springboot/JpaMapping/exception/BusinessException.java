package learning.springboot.JpaMapping.exception;

public class BusinessException extends ApplicationException{
    public BusinessException(String message){
        super(message, "LOGIC_ERROR");
    }
}
