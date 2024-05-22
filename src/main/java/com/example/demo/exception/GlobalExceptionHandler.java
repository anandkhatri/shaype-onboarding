package com.example.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import shaype.openapi.example.model.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ShaypeApiException.class)
    public ResponseEntity<ErrorResponse> handleShaypeApiException(ShaypeApiException ex) {
        ErrorResponse errorResponse = ex.getErrorResponse();
        return ResponseEntity.status(Integer.parseInt(errorResponse.getStatus())).body(errorResponse);
    }
}
