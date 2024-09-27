package com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProvidersConfig providersConfig;
    private final UserJwtFilter userJwtFilter;
    private final AdminJwtFilter adminJwtFilter;

    public SecurityConfig(AuthenticationProvidersConfig providersConfig, UserJwtFilter userJwtFilter, AdminJwtFilter adminJwtFilter) {
        this.providersConfig = providersConfig;
        this.userJwtFilter = userJwtFilter;
        this.adminJwtFilter = adminJwtFilter;
    }

    // User-specific security chain
    @Bean
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/user/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/register", "/user/login").permitAll()
                        .anyRequest().hasRole("USER")
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults());

        http.authenticationProvider(providersConfig.userAuthenticationProvider());
        http.addFilterBefore(userJwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Admin-specific security chain
    @Bean
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/admin/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/register", "/admin/login").permitAll()
                        .anyRequest().hasRole("ADMIN")
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults());

        http.authenticationProvider(providersConfig.adminAuthenticationProvider());
        http.addFilterBefore(adminJwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(@NotNull AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); // Configure to use userAuthenticationProvider
    }
}
