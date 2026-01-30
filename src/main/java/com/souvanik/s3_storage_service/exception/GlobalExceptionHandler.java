package com.souvanik.s3_storage_service.exception;

import com.souvanik.s3_storage_service.model.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ApiErrorResponse> handleValidation(Exception ex, HttpServletRequest req) {

        return ResponseEntity.badRequest().body(
                new ApiErrorResponse(
                        "VALIDATION_ERROR",
                        ex.getMessage(),
                        req.getRequestURI(),
                        null
                )
        );
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<ApiErrorResponse> handle(StorageException ex, HttpServletRequest req){
        return ResponseEntity.status(500)
                .body(new ApiErrorResponse("STORAGE_ERROR",ex.getMessage(),req.getRequestURI(),null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAll(Exception ex, HttpServletRequest req){
        return ResponseEntity.status(500)
                .body(new ApiErrorResponse("INTERNAL_ERROR","Unexpected error",req.getRequestURI(),null));
    }
}
