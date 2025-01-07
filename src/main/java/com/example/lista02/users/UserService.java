package com.example.lista02.users;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean userExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    public void createUser(String username, String password, String role) {

        if (userExists(username)) {
            throw new RuntimeException("Username already exists: " + username);
        }
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .enabled(true) // Explicitly setting enabled
                .build();
        userRepository.save(user);
    }

    public void seedUsers() {
        if (!userExists("user")) {
            createUser("user", "user", "ROLE_USER");
        }
        if (!userExists("admin")) {
            createUser("admin", "admin", "ROLE_ADMIN");
        }
    }
}
