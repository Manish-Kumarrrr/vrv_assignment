package com.manish.rbac.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This is a REST controller that handles requests related to different user roles.
 * The controller has different endpoints for users with roles: User, Moderator, and Admin.
 */
@RestController
@RequestMapping("/v1/resources")  // Base path for resource-related endpoints
@Tag(name = "Resource Controller", description = "APIs for accessing resources based on user roles")
public class ResourceController {

    /**
     * Endpoint to send a welcome message to users with the 'user' role.
     *
     * @return ResponseEntity with a welcome message for 'user'.
     */
    @GetMapping("/user")
    @Operation(
            summary = "Welcome message for users",
            description = "Returns a welcome message for users with the 'user' role.",
            parameters = {
                    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Bearer token for authentication", required = true)
            }
    )
    public ResponseEntity<String> messageToUser() {
        return ResponseEntity.ok("Welcome, User!!\n You are here because you have user's privilege.\n Explore the platform and enjoy your experience!");
    }

    /**
     * Endpoint to send a welcome message to users with the 'moderator' role.
     *
     * @return ResponseEntity with a welcome message for 'moderator'.
     */
    @GetMapping("/moderator")
    @Operation(
            summary = "Welcome message for moderators",
            description = "Returns a welcome message for users with the 'moderator' role.",
            parameters = {
                    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Bearer token for authentication", required = true)
            }
    )
    public ResponseEntity<String> messageToModerator() {
        return ResponseEntity.ok("Welcome, Moderator!!\n You are here because you have moderator's privilege.\n Explore the platform and enjoy your experience!");
    }

    /**
     * Endpoint to send a welcome message to users with the 'admin' role.
     *
     * @return ResponseEntity with a welcome message for 'admin'.
     */
    @GetMapping("/admin")
    @Operation(
            summary = "Welcome message for admins",
            description = "Returns a welcome message for users with the 'admin' role.",
            parameters = {
                    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Bearer token for authentication", required = true)
            }

    )
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<String> messageToAdmin() {
        return ResponseEntity.ok("Welcome, Admin!!\n You are here because you have admin's privilege.\n Explore the platform and enjoy your experience!");
    }
}
