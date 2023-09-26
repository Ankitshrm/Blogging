package com.bloggingbackend.exceptions;

import com.bloggingbackend.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception) {
        String message = exception.getMessage();
        ApiResponse response = new ApiResponse(message, false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApiExceptions.class)
    public ResponseEntity<ApiResponse> handleApiException(ResourceNotFoundException exception) {
        String message = exception.getMessage();
        ApiResponse response = new ApiResponse(message, true);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<Map<String,String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex){
        Map<String,String> map = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((it)->{
            String message = it.getDefaultMessage();
            String fieldName =((FieldError)it).getField();
            map.put(fieldName,message);
        });

        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }
}
