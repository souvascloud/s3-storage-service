package com.souvanik.s3_storage_service.model;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class FileResponse {
    private String key;

    public FileResponse() {
    }

    public void setKey(String key) {
        this.key = key;
    }

    public FileResponse(String key) { this.key = key; }
    public String getKey() { return key; }
}
