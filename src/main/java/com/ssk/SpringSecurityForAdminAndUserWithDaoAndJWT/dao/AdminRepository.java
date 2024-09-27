package com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.dao;

import com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer> {
    Admin findByUsername(String username);
}
