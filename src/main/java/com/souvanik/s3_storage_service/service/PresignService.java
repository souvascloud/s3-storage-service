package com.souvanik.s3_storage_service.service;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public interface PresignService {
    String generateUploadUrl(String fileName);
    String generateDownloadUrl(String key);
}
