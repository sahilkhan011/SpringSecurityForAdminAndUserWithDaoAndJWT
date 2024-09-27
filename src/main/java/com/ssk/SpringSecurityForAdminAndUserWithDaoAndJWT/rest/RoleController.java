package com.ssk.SpringSecurityForAdminAndUserWithDaoAndJWT.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

    @GetMapping("user/greet")
    public String userGreet(){
        return "Hello User.. Welcome to app";
    }
    @GetMapping("admin/greet")
    public String adminGreet(){
        return "Hello Admin.. Welcome to app";
    }

}
