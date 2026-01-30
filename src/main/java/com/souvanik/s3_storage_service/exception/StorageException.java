package com.souvanik.s3_storage_service.exception;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */

public class StorageException extends RuntimeException {
    public StorageException(String msg, Throwable cause){ super(msg,cause); }
}