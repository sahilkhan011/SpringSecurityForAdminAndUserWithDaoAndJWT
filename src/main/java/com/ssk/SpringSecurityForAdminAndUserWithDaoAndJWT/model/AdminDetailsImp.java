package com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class AdminDetailsImp implements UserDetails {
    private static final long serialVersionUID = 1L;

    private final Admin admin;

    public AdminDetailsImp(Admin admin) {
        this.admin = admin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return the user's role dynamically from the database.
        // We prefix the role with "ROLE_" as per Spring Security conventions.
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + admin.getRole()));
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
