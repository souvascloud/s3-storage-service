package com.souvanik.s3_storage_service.model;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public record PresignUploadResult(
        String url,
        String key
) {}