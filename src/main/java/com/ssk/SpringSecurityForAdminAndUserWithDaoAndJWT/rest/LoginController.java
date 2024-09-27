package com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.rest;

import com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.model.Admin;
import com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.model.User;
import com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.service.AdminService;
import com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.service.JwtService;
import com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    private final UserService userService;
    private final AdminService adminService;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    @Autowired
    public LoginController(UserService userService,
                           AdminService adminService,
                           AuthenticationManager authManager,
                           JwtService jwtService) {
        this.userService = userService;
        this.adminService = adminService;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    // Endpoint for user registration
    @PostMapping("user/register")
    public User registerUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // Endpoint for admin registration
    @PostMapping("admin/register")
    public Admin registerAdmin(@RequestBody Admin admin) {
        return adminService.createAdmin(admin);
    }

    // Endpoint for user login
    @PostMapping("user/login")
    public String loginUser(@RequestBody User user) throws Exception {
        // Call the custom login method for user authentication
        User authenticatedUser = userService.loginUser(user.getUsername(), user.getPassword());

        // If authentication is successful, generate a JWT token
        return jwtService.generateToken(authenticatedUser.getUsername(),authenticatedUser.getRole());
    }

    // Endpoint for admin login
    @PostMapping("admin/login")
    public String loginAdmin(@RequestBody Admin admin) throws Exception {
        // Call the custom login method for admin authentication
        Admin authenticatedAdmin = adminService.loginAdmin(admin.getUsername(), admin.getPassword());

        // If authentication is successful, generate a JWT token
        return jwtService.generateToken(authenticatedAdmin.getUsername(),authenticatedAdmin.getRole());
    }


}
