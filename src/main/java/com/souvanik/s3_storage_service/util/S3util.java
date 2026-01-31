package com.souvanik.s3_storage_service.util;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class S3util {
    
    public static String extractFileName(String key) {
        return key.contains("/")
                ? key.substring(key.lastIndexOf('/') + 1)
                : key;
    }
    public static String sanitizeFileName(String fileName) {
        return fileName
                .replace("\\", "")
                .replace("/", "")
                .replace("..", "");
    }

}
