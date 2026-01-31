package com.souvanik.s3_storage_service.model;

import jakarta.validation.constraints.NotBlank;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public record PresignDownloadRequest(
        @NotBlank(message = "key is required")
        String key
) {}