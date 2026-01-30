package com.souvanik.s3_storage_service.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
@Configuration
@EnableConfigurationProperties(S3Properties.class)
public class S3ClientConfig {

    @Bean
    public S3Client s3Client(S3Properties props) {
        return S3Client.builder()
                .region(Region.of(props.getRegion()))
                .build();
    }

    @Bean
    public S3Presigner s3Presigner(S3Properties props) {
        return S3Presigner.builder()
                .region(Region.of(props.getRegion()))
                .build();
    }

}
