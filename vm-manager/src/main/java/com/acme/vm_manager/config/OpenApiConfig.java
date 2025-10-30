package com.acme.vm_manager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    OpenAPI apiInfo() {
        return new OpenAPI().info(new Info()
                .title("VM Manager API")
                .description("CRUD de Máquinas Virtuais com operações de status e logs")
                .version("v1"));
    }
}
