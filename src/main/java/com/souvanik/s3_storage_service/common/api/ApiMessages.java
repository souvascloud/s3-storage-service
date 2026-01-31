package com.souvanik.s3_storage_service.common.api;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public final class ApiMessages {

    private ApiMessages() {}

    // Success
    public static final String SUCCESS = "SUCCESS";
    public static final String FILE_UPLOADED = "File uploaded";

    public static final String PRESIGNED_UPLOAD_URL = "Presigned upload URL";
    public static final String PRESIGNED_DOWNLOAD_URL = "Presigned download URL";
    // Validation
    public static final String FILE_REQUIRED = "File is required";
    public static final String FILE_EMPTY = "File cannot be empty";
    public static final String INVALID_FILE_NAME = "Invalid file name";
    public static final String S3_KEY_REQUIRED = "S3 key is required";
}
