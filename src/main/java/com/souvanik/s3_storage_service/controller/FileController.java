package com.souvanik.s3_storage_service.controller;

import com.souvanik.s3_storage_service.model.ApiSuccessResponse;
import com.souvanik.s3_storage_service.model.FileResponse;
import com.souvanik.s3_storage_service.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Tag(name = "Direct File APIs", description = "Files handled via API (API → S3)")
@RestController
@RequestMapping("/api/v1/files")
public class FileController {

    private final FileStorageService storageService;

    public FileController(FileStorageService storageService) {
        this.storageService = storageService;
    }



    @Operation(
            summary = "Upload file (Direct)",
            description = "Uploads file to S3 via backend API using IAM Role",
            responses = {
                    @ApiResponse(responseCode = "200", description = "File uploaded successfully"),
                    @ApiResponse(responseCode = "500", description = "Upload failed")
            }
    )
    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiSuccessResponse<FileResponse> upload(@Parameter(description = "File to upload", required = true)
                                                       @RequestParam("file")  @NotNull(message = "File is required") MultipartFile file) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        if (file.getOriginalFilename() == null || file.getOriginalFilename().isBlank()) {
            throw new IllegalArgumentException("Invalid file name");
        }
        String key = storageService.upload(file);
        return new ApiSuccessResponse<>("SUCCESS","File uploaded", new FileResponse(key), null);
    }




    @Operation(
            summary = "Download file (Direct)",
            description = "Downloads file from private S3 via backend API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "File downloaded"),
                    @ApiResponse(responseCode = "404", description = "File not found")
            }
    )
    @GetMapping(
            value = "/download",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public ResponseEntity<byte[]> download(
            @Parameter(description = "S3 object key", required = true)
            @RequestParam("key")  @NotBlank(message = "S3 key is required")  String key) throws Exception {

        InputStream is = storageService.download(key);
        return ResponseEntity.ok(is.readAllBytes());
    }
}