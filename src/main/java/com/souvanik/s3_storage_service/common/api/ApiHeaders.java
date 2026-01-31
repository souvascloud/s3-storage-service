package com.souvanik.s3_storage_service.common.api;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public final class ApiHeaders {

    private ApiHeaders() {}

    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String ATTACHMENT_FILENAME = "attachment; filename=\"%s\"";
}