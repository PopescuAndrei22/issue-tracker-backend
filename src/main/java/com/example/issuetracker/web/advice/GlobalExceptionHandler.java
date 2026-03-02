package com.example.issuetracker.web.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.issuetracker.web.dto.error.ApiError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> defaultHandler(Exception ex){
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error has occured!");
    }

    private ResponseEntity<ApiError> buildResponse(HttpStatus status, String message){
        ApiError error = new ApiError(
            status.value(),
            status.getReasonPhrase(),
            message
        );
        return new ResponseEntity<>(error, status);
    }
}
