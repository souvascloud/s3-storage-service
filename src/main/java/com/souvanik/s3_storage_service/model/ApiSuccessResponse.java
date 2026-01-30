package com.souvanik.s3_storage_service.model;

import java.time.Instant;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class ApiSuccessResponse<T> {
    private String code;
    private String message;
    private T data;
    private Instant timestamp = Instant.now();
    private String traceId;
    public ApiSuccessResponse(String code,String message,T data,String traceId){
        this.code=code;this.message=message;this.data=data;this.traceId=traceId;
    }
}