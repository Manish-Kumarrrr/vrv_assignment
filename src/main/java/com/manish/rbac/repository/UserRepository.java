package com.manish.rbac.repository;

import com.manish.rbac.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for interacting with the 'users' collection in MongoDB.
 * This interface extends MongoRepository, providing CRUD operations for the User entity.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    // MongoRepository already provides methods like save(), findById(), deleteById(), etc.
    // Additional custom queries can be defined here if needed in the future.
}
