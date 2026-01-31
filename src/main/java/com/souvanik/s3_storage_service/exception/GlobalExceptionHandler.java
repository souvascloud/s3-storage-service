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


    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ApiErrorResponse> handleValidation(
            Exception ex,
            HttpServletRequest request) {

        log.warn(
                "Validation error | path={} | message={}",
                request.getRequestURI(),
                ex.getMessage()
        );

        return ResponseEntity.badRequest().body(
                new ApiErrorResponse(
                        ApiErrorCodes.VALIDATION_ERROR,
                        ex.getMessage(),
                        request.getRequestURI(),
                        null
                )
        );
    }


    @ExceptionHandler({
            MissingServletRequestPartException.class,
            MaxUploadSizeExceededException.class
    })
    public ResponseEntity<ApiErrorResponse> handleMultipartErrors(
            Exception ex,
            HttpServletRequest request) {

        String message;

        if (ex instanceof MissingServletRequestPartException) {
            message = ApiErrorMessages.FILE_PART_MISSING;
        } else if (ex instanceof MaxUploadSizeExceededException) {
            message = ApiErrorMessages.FILE_SIZE_EXCEEDED;
        } else {
            message = ApiErrorMessages.INVALID_MULTIPART;
        }

        log.warn(
                "Multipart error | path={} | message={}",
                request.getRequestURI(),
                message
        );

        return ResponseEntity.badRequest().body(
                new ApiErrorResponse(
                        ApiErrorCodes.MULTIPART_ERROR,
                        message,
                        request.getRequestURI(),
                        null
                )
        );
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ApiErrorResponse> handleNotAcceptable(
            HttpMediaTypeNotAcceptableException ex,
            HttpServletRequest request) {

        log.warn(
                "Not acceptable | path={} | accept={}",
                request.getRequestURI(),
                request.getHeader(HttpHeaders.ACCEPT)
        );

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                new ApiErrorResponse(
                        ApiErrorCodes.NOT_ACCEPTABLE,
                        ApiErrorMessages.NOT_ACCEPTABLE,
                        request.getRequestURI(),
                        null
                )
        );

    }


    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> handleNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpServletRequest request) {

        log.warn(
                "Unsupported media type | path={} | contentType={}",
                request.getRequestURI(),
                request.getContentType()
        );

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(
                new ApiErrorResponse(
                        ApiErrorCodes.UNSUPPORTED_MEDIA_TYPE,
                        ApiErrorMessages.UNSUPPORTED_MEDIA_TYPE,
                        request.getRequestURI(),
                        null
                )
        );
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<ApiErrorResponse> handle(StorageException ex, HttpServletRequest req){

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorResponse(
                        ApiErrorCodes.STORAGE_ERROR,
                        ex.getMessage(),
                        req.getRequestURI(),
                        null
                )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAll(Exception ex, HttpServletRequest request){
        log.error(
                "Unhandled exception | path={}",
                request.getRequestURI(),
                ex
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON).body(
                new ApiErrorResponse(
                        ApiErrorCodes.INTERNAL_ERROR,
                        ApiErrorMessages.UNEXPECTED_ERROR,
                        request.getRequestURI(),
                        null
                )
        );
    }


}
