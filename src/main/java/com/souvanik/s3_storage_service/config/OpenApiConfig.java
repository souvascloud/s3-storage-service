package com.souvanik.s3_storage_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

/*
 * Copyright (c) 2026 Souvanik Saha
 *
 * Licensed under the MIT License.
 * https://opensource.org/licenses/MIT
 */
public class OpenApiConfig {

    @Bean
    public OpenAPI storageServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Souva S3 Storage Service API")
                        .description("Hybrid Storage API (Direct Upload/Download + Presigned URLs)")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Souvanik Saha")
                                .email("devsouva@gmail.com")
                        )
                );
    }
}
