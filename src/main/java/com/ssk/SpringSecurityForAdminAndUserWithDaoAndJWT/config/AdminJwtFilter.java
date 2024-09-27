package com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.config;

import com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.service.AdminDetailsServiceImp;
import com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AdminJwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final AdminDetailsServiceImp adminSer;

    @Autowired
    public AdminJwtFilter(JwtService jwtService, AdminDetailsServiceImp adminSer) {
        this.jwtService = jwtService;
        this.adminSer = adminSer;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            userName = jwtService.extractUserName(token);
        }

        // Validate token and authenticate if necessary
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = adminSer.loadUserByUsername(userName);

            // Extract the role from the token
            String tokenRole = jwtService.extractRole(token);

            // Validate the token and check if the user has the right role
            if (jwtService.validateToken(token, userDetails) && userDetails.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_" +tokenRole))) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                // If role doesn't match, return forbidden
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied: Role Mismatch");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
