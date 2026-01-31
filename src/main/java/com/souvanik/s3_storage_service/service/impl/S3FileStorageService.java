package com.souvanik.s3_storage_service.service.impl;

import com.souvanik.s3_storage_service.config.S3Properties;
import com.souvanik.s3_storage_service.exception.StorageException;
import com.souvanik.s3_storage_service.model.S3DownloadObject;
import com.souvanik.s3_storage_service.service.FileStorageService;
import com.souvanik.s3_storage_service.util.S3util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;
import java.util.UUID;



/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
@Service
public class S3FileStorageService implements FileStorageService {

    private static final Logger log = LoggerFactory.getLogger(S3FileStorageService.class);

    private final S3Client s3Client;
    private final S3Properties props;

    public S3FileStorageService(S3Client s3Client, S3Properties props) {
        this.s3Client = s3Client;
        this.props = props;
    }

    @Override
    public String upload(MultipartFile file) {
        try {
            String key = props.getBasePath() + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(props.getBucketName())
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(request,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            log.info("Direct upload complete | key={}", key);
            return key;

        } catch (Exception e) {
            log.error("Direct upload failed", e);
            throw new StorageException("Upload failed", e);
        }
    }

    @Override
    public S3DownloadObject download(String key) {
        try {
            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(props.getBucketName())
                    .key(key)
                    .build();

            ResponseInputStream<GetObjectResponse> response =
                    s3Client.getObject(request);

            GetObjectResponse metadata = response.response();

            return new S3DownloadObject(
                    response,
                    extractFileName(key),
                    metadata.contentType() != null
                            ? metadata.contentType()
                            : MediaType.APPLICATION_OCTET_STREAM_VALUE,
                    metadata.contentLength()
            );

        } catch (Exception e) {
            log.error("Download failed for key={}", key, e);
            throw new StorageException("Download failed", e);
        }
    }

    private String extractFileName(String key) {
        return S3util.extractFileName(key);
    }
}
