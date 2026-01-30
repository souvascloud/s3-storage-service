package com.souvanik.s3_storage_service.model;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class PresignResponse {

    private String url;
    private String key;
    public PresignResponse(String url, String key) {
        this.url = url;
        this.key = key;
    }
    public String getUrl() { return url; }
    public String getKey() { return key; }
}
