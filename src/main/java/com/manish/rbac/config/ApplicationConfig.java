package com.manish.rbac.config;

import com.manish.rbac.model.User;
import com.manish.rbac.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // Indicates that this class provides configuration to the Spring container
@RequiredArgsConstructor // Lombok annotation to generate a constructor with required arguments for final fields
public class ApplicationConfig {

    private final UserRepository userRepository; // The user repository for fetching user data

    /**
     * This bean provides a custom UserDetailsService to load user details by their ID (email).
     * The service fetches the user from the database and converts it into a Spring Security UserDetails object.
     *
     * @return UserDetailsService Implementation to load user by ID
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return id -> {
            // Fetch user from the database by id (email in this case)
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

            // Convert the User model object to a Spring Security UserDetails object
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail()) // Use email as the username
                    .password(user.getPassword()) // The encoded password
                    .roles(user.getRoles()) // Set user roles (e.g., "ROLE_USER")
                    .build();
        };
    }

    /**
     * Bean that provides the AuthenticationManager, used for authenticating users.
     * It is automatically configured by Spring Security based on the provided authentication configuration.
     *
     * @param config The AuthenticationConfiguration to get the authentication manager from
     * @return AuthenticationManager
     * @throws Exception If an error occurs during the creation of the authentication manager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Bean that provides a password encoder using BCrypt hashing algorithm.
     * It is used to encode passwords before storing them in the database and to validate password matches during authentication.
     *
     * @return PasswordEncoder for encoding passwords
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // Use BCrypt to securely encode passwords
    }
}
