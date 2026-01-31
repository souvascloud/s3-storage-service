package com.souvanik.s3_storage_service.common.api;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public final class ApiPaths {

    private ApiPaths() {}

    public static final String FILES_BASE = "/api/v1/files";
    public static final String PRESIGN_BASE = FILES_BASE + "/presign";

    public static final String UPLOAD = "/upload";
    public static final String DOWNLOAD = "/download";
}