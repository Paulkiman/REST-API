package com.project.book_inventory_api.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration // Marks this as a configuration class
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact()
                .name("Book Inventory API")
                .email("PaulKimani203@gmail.com");

        Info info = new Info()
                .title("Book Inventory API")
                .version("1.1")
                .description("This API exposes endpoints to automate Book Keeping.")
                .contact(contact);

        io.swagger.v3.oas.models.security.SecurityScheme bearerAuth =new io.swagger.v3.oas.models.security.SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        return new OpenAPI()
                .info(info)
                .components(new Components().addSecuritySchemes("bearerAuth", bearerAuth));
    }
}
