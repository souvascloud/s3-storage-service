package com.souvanik.s3_storage_service.controller;

import com.souvanik.s3_storage_service.common.api.ApiMessages;
import com.souvanik.s3_storage_service.common.api.ApiPaths;
import com.souvanik.s3_storage_service.common.api.SwaggerConstants;
import com.souvanik.s3_storage_service.model.ApiSuccessResponse;
import com.souvanik.s3_storage_service.model.PresignResponse;
import com.souvanik.s3_storage_service.model.PresignUploadRequest;
import com.souvanik.s3_storage_service.model.PresignUploadResult;
import com.souvanik.s3_storage_service.service.PresignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
@Tag(
        name = SwaggerConstants.TAG_PRESIGN,
        description = SwaggerConstants.TAG_PRESIGN_DESC
)
@RestController
@RequestMapping(ApiPaths.PRESIGN_BASE)
public class PresignController {

    private final PresignService presignService;

    public PresignController(PresignService presignService) {
        this.presignService = presignService;
    }

    @Operation(
            summary = SwaggerConstants.PRESIGN_UPLOAD_SUMMARY,
            description = SwaggerConstants.PRESIGN_UPLOAD_DESC
    )
    @PostMapping(
            value = ApiPaths.UPLOAD,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiSuccessResponse<PresignResponse> presignUpload(
            @Valid @RequestBody PresignUploadRequest request) {

        PresignUploadResult result =
                presignService.generateUploadUrl(
                        request.fileName(),
                        request.contentType()
                );

        return new ApiSuccessResponse<>(
                ApiMessages.SUCCESS,
                ApiMessages.PRESIGNED_UPLOAD_URL,
                new PresignResponse(result.url(), result.key()),
                null
        );
    }



    @Operation(
            summary = SwaggerConstants.PRESIGN_DOWNLOAD_SUMMARY,
            description = SwaggerConstants.PRESIGN_DOWNLOAD_DESC
    )
    @GetMapping(
            value = ApiPaths.DOWNLOAD,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiSuccessResponse<PresignResponse> presignDownload(
             @Valid @RequestParam("key") String key) {

        String url = presignService.generateDownloadUrl(key);

        return new ApiSuccessResponse<>(
                ApiMessages.SUCCESS,
                ApiMessages.PRESIGNED_DOWNLOAD_URL,
                new PresignResponse(url, key),
                null
        );
    }
}