package com.souvanik.s3_storage_service.exception;

import com.souvanik.s3_storage_service.common.error.ApiErrorCodes;
import com.souvanik.s3_storage_service.common.error.ApiErrorMessages;
import com.souvanik.s3_storage_service.model.ApiErrorResponse;
import com.souvanik.s3_storage_service.service.impl.S3FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.time.Instant;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    private ResponseEntity<ApiErrorResponse> json(
            HttpStatus status,
            ApiErrorResponse body) {

        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ApiErrorResponse> handleValidation(
            Exception ex,
            HttpServletRequest request) {

        log.warn("Validation error | path={} | msg={}",
                request.getRequestURI(), ex.getMessage());

        return json(HttpStatus.BAD_REQUEST,
                new ApiErrorResponse(
                        ApiErrorCodes.VALIDATION_ERROR,
                        ex.getMessage(),
                        request.getRequestURI(),
                        null
                ));
    }

    @ExceptionHandler({
            MissingServletRequestPartException.class,
            MaxUploadSizeExceededException.class
    })
    public ResponseEntity<ApiErrorResponse> handleMultipartErrors(
            Exception ex,
            HttpServletRequest request) {

        String message = ex instanceof MissingServletRequestPartException
                ? ApiErrorMessages.FILE_PART_MISSING
                : ApiErrorMessages.FILE_SIZE_EXCEEDED;

        log.warn("Multipart error | path={} | msg={}",
                request.getRequestURI(), message);

        return json(HttpStatus.BAD_REQUEST,
                new ApiErrorResponse(
                        ApiErrorCodes.MULTIPART_ERROR,
                        message,
                        request.getRequestURI(),
                        null
                ));
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ApiErrorResponse> handleNotAcceptable(
            HttpServletRequest request) {

        return json(HttpStatus.NOT_ACCEPTABLE,
                new ApiErrorResponse(
                        ApiErrorCodes.NOT_ACCEPTABLE,
                        ApiErrorMessages.NOT_ACCEPTABLE,
                        request.getRequestURI(),
                        null
                ));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> handleNotSupported(
            HttpServletRequest request) {

        return json(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                new ApiErrorResponse(
                        ApiErrorCodes.UNSUPPORTED_MEDIA_TYPE,
                        ApiErrorMessages.UNSUPPORTED_MEDIA_TYPE,
                        request.getRequestURI(),
                        null
                ));
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<ApiErrorResponse> handleStorage(
            StorageException ex,
            HttpServletRequest request) {

        return json(HttpStatus.INTERNAL_SERVER_ERROR,
                new ApiErrorResponse(
                        ApiErrorCodes.STORAGE_ERROR,
                        ex.getMessage(),
                        request.getRequestURI(),
                        null
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAll(
            Exception ex,
            HttpServletRequest request) {

        log.error("Unhandled exception | path={}",
                request.getRequestURI(), ex);

        return json(HttpStatus.INTERNAL_SERVER_ERROR,
                new ApiErrorResponse(
                        ApiErrorCodes.INTERNAL_ERROR,
                        ApiErrorMessages.UNEXPECTED_ERROR,
                        request.getRequestURI(),
                        null
                ));
    }

}
