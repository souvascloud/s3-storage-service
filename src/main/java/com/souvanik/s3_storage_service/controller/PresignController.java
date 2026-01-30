package com.souvanik.s3_storage_service.controller;

import com.souvanik.s3_storage_service.model.ApiSuccessResponse;
import com.souvanik.s3_storage_service.model.PresignResponse;
import com.souvanik.s3_storage_service.service.PresignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
@Tag(name = "Presigned APIs", description = "Presigned URL APIs (Client → S3 direct access)")
@RestController
@RequestMapping("/api/v1/files/presign")
public class PresignController {

    private final PresignService presignService;

    public PresignController(PresignService presignService) {
        this.presignService = presignService;
    }

    @Operation(
            summary = "Generate presigned upload URL",
            description = "Generates a temporary PUT URL for direct client upload to S3",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Presigned upload URL generated"),
                    @ApiResponse(responseCode = "500", description = "Failed to generate URL")
            }
    )
    @PostMapping("/upload")
    public ApiSuccessResponse<PresignResponse> presignUpload(
            @Parameter(description = "Original file name", required = true)
            @NotBlank(message = "fileName is required") String fileName) {

        if (fileName.contains("..") || fileName.contains("/")) {
            throw new IllegalArgumentException("Invalid file name");
        }

        String url = presignService.generateUploadUrl(fileName);
        return new ApiSuccessResponse<>("SUCCESS","Presigned upload URL",
                new PresignResponse(url, null), null);
    }



    @Operation(
            summary = "Generate presigned download URL",
            description = "Generates a temporary GET URL for direct client download from S3",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Presigned download URL generated"),
                    @ApiResponse(responseCode = "500", description = "Failed to generate URL")
            }
    )
    @GetMapping("/download")
    public ApiSuccessResponse<PresignResponse> presignDownload(
            @Parameter(description = "S3 object key", required = true)
            @NotBlank(message = "key is required") String key) {

        String url = presignService.generateDownloadUrl(key);
        return new ApiSuccessResponse<>("SUCCESS","Presigned download URL",
                new PresignResponse(url, key), null);
    }
}