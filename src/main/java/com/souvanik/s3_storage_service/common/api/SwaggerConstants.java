package com.souvanik.s3_storage_service.common.api;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public final class SwaggerConstants {

    private SwaggerConstants() {}

    /* ===== Tags ===== */

    public static final String TAG_DIRECT_FILES = "Direct File APIs";
    public static final String TAG_DIRECT_FILES_DESC =
            "Files handled via API (API → S3)";

    public static final String TAG_PRESIGN = "Presigned APIs";
    public static final String TAG_PRESIGN_DESC =
            "Presigned URL APIs (Client → S3 direct access)";

    /* ===== Operations ===== */

    public static final String UPLOAD_DIRECT_SUMMARY = "Upload file (Direct)";
    public static final String UPLOAD_DIRECT_DESC =
            "Uploads file to S3 via backend API using IAM Role";

    public static final String DOWNLOAD_DIRECT_SUMMARY = "Download file (Direct)";
    public static final String DOWNLOAD_DIRECT_DESC =
            "Downloads file from private S3 via backend API";

    public static final String PRESIGN_UPLOAD_SUMMARY =
            "Generate presigned upload URL";
    public static final String PRESIGN_UPLOAD_DESC =
            "Generates a temporary PUT URL for direct client upload to S3";

    public static final String PRESIGN_DOWNLOAD_SUMMARY =
            "Generate presigned download URL";
    public static final String PRESIGN_DOWNLOAD_DESC =
            "Generates a temporary GET URL for direct client download from S3";
}
