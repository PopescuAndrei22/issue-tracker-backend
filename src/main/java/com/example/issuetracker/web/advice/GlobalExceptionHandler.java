package com.example.issuetracker.web.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.issuetracker.exceptions.ResourceNotFoundException;
import com.example.issuetracker.web.dto.error.ApiError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleDtoValidationErrors(MethodArgumentNotValidException ex) {
        String firstError = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return buildResponse(HttpStatus.BAD_REQUEST, "Validation failed! Reason: " + firstError);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleDtoValidationErrors(ResourceNotFoundException ex) {
        String message = ex.getResource() + "with ID: " + ex.getId() + " not found.";
        return buildResponse(HttpStatus.NOT_FOUND, message);

    }

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
