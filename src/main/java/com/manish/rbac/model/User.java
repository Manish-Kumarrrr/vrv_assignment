package com.manish.rbac.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@AllArgsConstructor
@Builder
@Document(collection = "users") // The collection name in MongoDB
public class User {

    @Id
    private String id; // Automatically mapped to MongoDB "_id"
    private String name;
    private String email;
    private String password;
    private List<String> roles;

}
