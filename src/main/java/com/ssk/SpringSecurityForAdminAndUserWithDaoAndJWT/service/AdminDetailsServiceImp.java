package com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.service;

import com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.dao.AdminRepository;
import com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.model.Admin;
import com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.model.AdminDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminDetailsServiceImp implements UserDetailsService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminDetailsServiceImp(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user by username from the database
        Admin admin = adminRepository.findByUsername(username);

        // If user not found, throw exception
        if (admin == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Return an instance of UserDetailsImp which now contains the role as well
        return new AdminDetailsImp(admin);
    }
}
