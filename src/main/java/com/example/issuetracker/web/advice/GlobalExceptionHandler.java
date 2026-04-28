package com.example.issuetracker.web.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.issuetracker.exceptions.ResourceNotFoundException;
import com.example.issuetracker.web.dto.error.ApiError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleDtoValidationErrors(MethodArgumentNotValidException ex) {
        String firstError = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

        log.warn("Validation failed: {}", ex.getMessage());

        return buildResponse(HttpStatus.BAD_REQUEST, "Validation failed! Reason: " + firstError);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundErrors(ResourceNotFoundException ex) {
        String message = ex.getResource() + " with ID: " + ex.getId() + " not found.";

        log.info("Resource not found: {} ID: {}", ex.getResource(), ex.getId());

        return buildResponse(HttpStatus.NOT_FOUND, message);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> defaultHandler(Exception ex){
        log.error("Unhandled exception occurred!", ex);

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
