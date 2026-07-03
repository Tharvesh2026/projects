package learning.springboot.JpaMapping.exception;

import learning.springboot.JpaMapping.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNotFound(ResourceNotFoundException ex) {

        return new ResponseEntity<>(
                new ApiResponse<>(ex.getMessage(), null),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<String>> handleBadRequest(BadRequestException ex) {

        return new ResponseEntity<>(
                new ApiResponse<>(ex.getMessage(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<String>> handleBusiness(BusinessException ex) {

        return new ResponseEntity<>(
                new ApiResponse<>(ex.getMessage(), null),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNoResource(NoResourceFoundException ex){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>("Endpoint not found", null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception ex){

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>("Something went wrong", null));
    }
}