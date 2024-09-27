# SpringSecurityForAdminAndUserWithDaoAndJWT

## Overview

This project is a Spring Boot application that implements security features using JWT (JSON Web Token) for authentication. It uses the DAO (Data Access Object) pattern to manage user data and secures API routes based on user roles.

## Features

- User registration and login functionality.
- JWT-based authentication for securing API endpoints.
- Role-based access control for different permissions for users and admins.

## Project Structure

- **src/main/java/com/ssk/SpringSecurityForAdminAndUserWithDaoAndJWT**
  - `config`: Contains configuration classes for security settings.
  - `controller`: Includes REST controllers for user and admin endpoints.
  - `entity`: Contains entity classes representing the database tables.
  - `repository`: Contains repository interfaces for database operations.
  - `service`: Includes service classes for handling business logic.

## Implementation Steps

### 1. Project Setup

- Created a Spring Boot application using Spring Initializr with dependencies for:
  - Spring Security
  - Spring Web
  - Spring Data JPA

### 2. Database Configuration

- Configured a database (e.g., MySQL) for user data storage.
- Updated `application.properties` with database connection details.

### 3. User Entity and Repository

- Created a `User` entity to represent user data.
- Implemented a `UserRepository` interface extending `JpaRepository` for data access.

### 4. UserDetailsService Implementation

- Developed `UserDetailsServiceImp` to load user-specific data and implement user authentication.

### 5. JWT Utility

- Created a utility class for generating and validating JWT tokens for secure authentication.

### 6. Security Configuration

- Configured Spring Security to manage authentication and authorization.
- Defined security rules for endpoints, allowing open access to `/register` and `/login` while securing other routes.

### 7. JWT Filter

- Implemented a `JwtFilter` to intercept requests, validate tokens, and authenticate users based on roles.
