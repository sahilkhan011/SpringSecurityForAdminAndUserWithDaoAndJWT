package com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.service;

import com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.dao.AdminRepository;
import com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(AdminRepository adminRepository, BCryptPasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 1. Create a new user with password encoding
    public Admin createAdmin(Admin admin) {
        // Encode the password before saving the user
        String encodedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encodedPassword);
        return adminRepository.save(admin);
    }

    // 2. Get a user by ID
    public Optional<Admin> getAdminById(int id) {
        return adminRepository.findById(id);
    }

    // 3. Get a user by username
    public Admin getAdminByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    // 4. Update an existing user with password encoding (if password is being changed)
    public Admin updateAdmin(int id, Admin updatedUser) {
        return adminRepository.findById(id)
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

                    return adminRepository.save(user);
                }).orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    // 5. Delete a user by ID
    public void deleteAdminById(int id) {
        adminRepository.deleteById(id);
    }

    // 6. Get all users
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    // 7. Custom login method that returns a User object
    public Admin loginAdmin(String username, String rawPassword) {
        // Retrieve the user by username
        Admin user = adminRepository.findByUsername(username);

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
