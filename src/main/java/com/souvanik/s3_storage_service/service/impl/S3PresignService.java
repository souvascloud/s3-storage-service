package com.souvanik.s3_storage_service.service.impl;

import com.souvanik.s3_storage_service.config.S3Properties;
import com.souvanik.s3_storage_service.service.PresignService;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.UUID;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
@Service
public class S3PresignService implements PresignService {

    private final S3Presigner presigner;
    private final S3Properties props;

    public S3PresignService(S3Presigner presigner, S3Properties props) {
        this.presigner = presigner;
        this.props = props;
    }

    @Override
    public String generateUploadUrl(String fileName) {

        String key = props.getBasePath() + "/" + UUID.randomUUID() + "-" + fileName;

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(props.getBucketName())
                .key(key)
                .build();

        PutObjectPresignRequest presignRequest =
                PutObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(props.getPresignExpiryMinutes()))
                        .putObjectRequest(request)
                        .build();

        return presigner.presignPutObject(presignRequest).url().toString();
    }

    @Override
    public String generateDownloadUrl(String key) {

        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(props.getBucketName())
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest =
                GetObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(props.getPresignExpiryMinutes()))
                        .getObjectRequest(request)
                        .build();

        return presigner.presignGetObject(presignRequest).url().toString();
    }
}
