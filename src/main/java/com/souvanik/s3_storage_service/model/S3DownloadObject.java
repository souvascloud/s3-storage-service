package com.souvanik.s3_storage_service.model;

import java.io.InputStream;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public record S3DownloadObject(
        InputStream inputStream,
        String fileName,
        String contentType,
        long contentLength
) {}