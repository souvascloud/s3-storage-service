package com.souvanik.s3_storage_service.model;

import java.time.Instant;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class ApiErrorResponse {
    private String code;
    private String message;
    private String path;
    private Instant timestamp = Instant.now();
    private String traceId;
    public ApiErrorResponse(String c,String m,String p,String t){
        this.code=c;this.message=m;this.path=p;this.traceId=t;
    }
}