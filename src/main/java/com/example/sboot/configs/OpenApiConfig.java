package com.example.sboot.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
@OpenAPIDefinition(
        info = @Info(
                title = "${application.title}",
                version = "${application.version}",
                description = "${application.description}",
                contact = @Contact(
                        url = "${application.author.contact.url}",
                        name = "${application.author.contact.name}",
                        email = "${application.author.contact.email}"
                )
        ),
        security = @SecurityRequirement(name = "basicAuth"), // references the name defined in @SecurityScheme
        servers = {
                @Server(
                        description = "Development Server",
                        url = "http://localhost:8080"),
                @Server(
                        description = "Beta Server",
                        url = "http://localhost:8082")}
)
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder().group("REST API").pathsToMatch("/api/**").pathsToExclude("/api/profile/**").build();
    }
}
