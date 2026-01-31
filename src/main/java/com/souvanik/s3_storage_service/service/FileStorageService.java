package com.souvanik.s3_storage_service.service;

import com.souvanik.s3_storage_service.model.S3DownloadObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public interface FileStorageService {
    String upload(MultipartFile file);
    S3DownloadObject download(String key);
}
