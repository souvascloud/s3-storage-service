package com.souvanik.s3_storage_service.service;

import com.souvanik.s3_storage_service.model.PresignUploadResult;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public interface PresignService {
    PresignUploadResult generateUploadUrl(String fileName, String contentType);
    String generateDownloadUrl(String key);
}
