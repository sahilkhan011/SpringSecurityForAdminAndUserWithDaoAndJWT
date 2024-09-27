package com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.config;

import com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.service.AdminDetailsServiceImp;
import com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.service.UserDetailsServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AuthenticationProvidersConfig {
    private final UserDetailsServiceImp userDetailsServiceImp;
    private final AdminDetailsServiceImp adminDetailsServiceImp;

    public AuthenticationProvidersConfig(UserDetailsServiceImp userDetailsServiceImp, AdminDetailsServiceImp adminDetailsServiceImp) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.adminDetailsServiceImp = adminDetailsServiceImp;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    // Authentication provider for users
    @Bean
    public AuthenticationProvider userAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsServiceImp);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // Authentication provider for admins
    @Bean
    public AuthenticationProvider adminAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(adminDetailsServiceImp);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
