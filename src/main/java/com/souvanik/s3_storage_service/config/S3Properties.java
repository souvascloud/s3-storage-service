package com.souvanik.s3_storage_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */

@ConfigurationProperties(prefix = "storage.s3")
public class S3Properties {

    private String bucketName;
    private String region;
    private String basePath;
    private int presignExpiryMinutes;

    public String getBucketName() { return bucketName; }
    public void setBucketName(String bucketName) { this.bucketName = bucketName; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getBasePath() { return basePath; }
    public void setBasePath(String basePath) { this.basePath = basePath; }

    public int getPresignExpiryMinutes() { return presignExpiryMinutes; }
    public void setPresignExpiryMinutes(int presignExpiryMinutes) {
        this.presignExpiryMinutes = presignExpiryMinutes;
    }
}
