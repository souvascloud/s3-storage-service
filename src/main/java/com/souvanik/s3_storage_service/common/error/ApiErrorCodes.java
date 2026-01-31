package com.souvanik.s3_storage_service.common.error;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class ApiErrorCodes {

    private ApiErrorCodes() {}

    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
    public static final String MULTIPART_ERROR = "MULTIPART_ERROR";
    public static final String NOT_ACCEPTABLE = "NOT_ACCEPTABLE";
    public static final String UNSUPPORTED_MEDIA_TYPE = "UNSUPPORTED_MEDIA_TYPE";
    public static final String STORAGE_ERROR = "STORAGE_ERROR";
    public static final String INTERNAL_ERROR = "INTERNAL_ERROR";

}
