package com.manish.rbac.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development");

        Contact myContact = new Contact();
        myContact.setName("Manish Kumar");
        myContact.setEmail("mkumar.work24@gmail.com");

        Info information = new Info()
                .title("Role Based Access Control(RBAC)")
                .version("1.0")
                .description("This API exposes endpoints to manage rbac.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}