package com.manish.rbac.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This is a REST controller that handles requests related to different user roles.
 * The controller has different endpoints for users with roles: User, Moderator, and Admin.
 */
@RestController
@RequestMapping("/v1/resources")  // Base path for resource-related endpoints

public class ResourceController {

    /**
     * Endpoint to send a welcome message to users with the 'user' role.
     * @return ResponseEntity with a welcome message for 'user'.
     */
    @GetMapping("/user")
    public ResponseEntity<String> messageToUser() {
        return ResponseEntity.ok("Welcome, User!!\n You are here because you have user's privilege.\n Explore the platform and enjoy your experience!");
    }

    /**
     * Endpoint to send a welcome message to users with the 'moderator' role.
     * @return ResponseEntity with a welcome message for 'moderator'.
     */
    @GetMapping("/moderator")
    public ResponseEntity<String> messageToModerator() {
        return ResponseEntity.ok("Welcome, Moderator!!\n You are here because you have moderator's privilege.\n Explore the platform and enjoy your experience!");
    }

    /**
     * Endpoint to send a welcome message to users with the 'admin' role.
     * @return ResponseEntity with a welcome message for 'admin'.
     */
    @GetMapping("/admin")
    public ResponseEntity<String> messageToAdmin() {
        return ResponseEntity.ok("Welcome, Admin!!\n You are here because you have admin's privilege.\n Explore the platform and enjoy your experience!");
    }

}
