package com.souvanik.s3_storage_service.common.error;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class ApiErrorMessages {

    private ApiErrorMessages() {}

    public static final String FILE_PART_MISSING =
            "Required file part is missing";

    public static final String FILE_SIZE_EXCEEDED =
            "File size exceeds configured limit";

    public static final String INVALID_MULTIPART =
            "Invalid multipart request";

    public static final String NOT_ACCEPTABLE =
            "Requested response format is not supported. Use Accept: application/json";

    public static final String UNSUPPORTED_MEDIA_TYPE =
            "Unsupported Content-Type. Use multipart/form-data for file upload";

    public static final String UNEXPECTED_ERROR =
            "Unexpected error";

}
