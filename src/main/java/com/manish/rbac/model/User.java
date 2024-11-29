package com.manish.rbac.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



/**
 * The User class represents a user in the system.
 * It is mapped to the 'users' collection in MongoDB.
 * This class holds user-specific information such as id, name, email, password, and roles.
 */
@Data // Lombok annotation to generate getter, setter, toString, equals, and hashCode methods
@AllArgsConstructor // Lombok annotation to generate a constructor with all fields as parameters
@Builder // Lombok annotation to provide a builder pattern for object creation
@Document(collection = "users") // Specifies the MongoDB collection name associated with this model
public class User {

    @Id // Marks this field as the unique identifier for the MongoDB document
    private String id; // Automatically mapped to MongoDB "_id"

    private String name; // User's name

    private String email; // User's email (used as username for authentication)

    private String password; // User's password (should be securely hashed)

    private String roles; // Roles assigned to the user, e.g., "USER", "ADMIN", "MODERATOR"
}
