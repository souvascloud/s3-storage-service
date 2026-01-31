package com.souvanik.s3_storage_service.controller;

import com.souvanik.s3_storage_service.common.api.ApiHeaders;
import com.souvanik.s3_storage_service.common.api.ApiMessages;
import com.souvanik.s3_storage_service.common.api.ApiPaths;
import com.souvanik.s3_storage_service.common.api.SwaggerConstants;
import com.souvanik.s3_storage_service.model.ApiSuccessResponse;
import com.souvanik.s3_storage_service.model.FileResponse;
import com.souvanik.s3_storage_service.model.S3DownloadObject;
import com.souvanik.s3_storage_service.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
@Tag(
        name = SwaggerConstants.TAG_DIRECT_FILES,
        description = SwaggerConstants.TAG_DIRECT_FILES_DESC
)
@RestController
@RequestMapping(ApiPaths.FILES_BASE)
public class FileController {

    private final FileStorageService storageService;

    public FileController(FileStorageService storageService) {
        this.storageService = storageService;
    }



    @Operation(
            summary = SwaggerConstants.UPLOAD_DIRECT_SUMMARY,
            description = SwaggerConstants.UPLOAD_DIRECT_DESC
    )
    @PostMapping(
            value = ApiPaths.UPLOAD,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiSuccessResponse<FileResponse>> upload(@Parameter(description = "File to upload", required = true)
                                                       @RequestParam("file")  @NotNull(message = ApiMessages.FILE_REQUIRED) MultipartFile file) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException(ApiMessages.FILE_EMPTY);
        }

        if (file.getOriginalFilename() == null ||
                file.getOriginalFilename().isBlank()) {
            throw new IllegalArgumentException(ApiMessages.INVALID_FILE_NAME);
        }

        String key = storageService.upload(file);

        return ResponseEntity.ok(new ApiSuccessResponse<>(
                ApiMessages.SUCCESS,
                ApiMessages.FILE_UPLOADED,
                new FileResponse(key),
                null
        ));
    }




    @Operation(
            summary = SwaggerConstants.DOWNLOAD_DIRECT_SUMMARY,
            description = SwaggerConstants.DOWNLOAD_DIRECT_DESC
    )
    @GetMapping(
            value = ApiPaths.DOWNLOAD,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public ResponseEntity<InputStreamResource> download(
            @Parameter(description = "S3 object key", required = true)
            @RequestParam("key")
            @NotBlank(message = ApiMessages.S3_KEY_REQUIRED)
            String key) {

        S3DownloadObject obj = storageService.download(key);

        return ResponseEntity.ok()
                .header(
                        ApiHeaders.CONTENT_DISPOSITION,
                        String.format(
                                ApiHeaders.ATTACHMENT_FILENAME,
                                obj.fileName()
                        )
                )
                .contentType(MediaType.parseMediaType(obj.contentType()))
                .contentLength(obj.contentLength())
                .body(new InputStreamResource(obj.inputStream()));
    }

}