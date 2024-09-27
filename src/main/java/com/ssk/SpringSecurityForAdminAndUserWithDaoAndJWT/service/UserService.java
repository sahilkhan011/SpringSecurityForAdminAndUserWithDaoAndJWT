package com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.service;

import com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.dao.UserRepository;
import com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService  {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 1. Create a new user with password encoding
    public User createUser(User user) {
        // Encode the password before saving the user
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    // 2. Get a user by ID
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    // 3. Get a user by username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // 4. Update an existing user with password encoding (if password is being changed)
    public User updateUser(int id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    // Update fields
                    user.setUsername(updatedUser.getUsername());
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());

                    // Encode the password only if it has been updated
                    if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                        String encodedPassword = passwordEncoder.encode(updatedUser.getPassword());
                        user.setPassword(encodedPassword);
                    }

                    return userRepository.save(user);
                }).orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    // 5. Delete a user by ID
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    // 6. Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 7. Custom login method that returns a User object
    public User loginUser(String username, String rawPassword) {
        // Retrieve the user by username
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found with username: " + username);
        }

        // Check if the provided password matches the encoded password
        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            // Password matches, return the user object
            return user;
        } else {
            // Password doesn't match, throw an exception
            throw new RuntimeException("Invalid credentials. Login failed.");
        }
    }



}
