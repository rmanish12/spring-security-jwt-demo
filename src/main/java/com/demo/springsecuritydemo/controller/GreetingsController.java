package com.demo.springsecuritydemo.controller;

import com.demo.springsecuritydemo.dto.AuthRequest;
import com.demo.springsecuritydemo.entity.UserInfo;
import com.demo.springsecuritydemo.service.JwtService;
import com.demo.springsecuritydemo.service.UserInfoAdditionalService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class GreetingsController {

    private UserInfoAdditionalService userInfoAdditionalService;
    private JwtService jwtService;

    @GetMapping("/welcome")
    public String greet() {
        return "Hello World!!";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return userInfoAdditionalService.addUser(userInfo);
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        return jwtService.generateToken(authRequest.getUserName());
    }

    @GetMapping("/admin/test")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String authEndPoint() {
        return "ADMIN ENDPOINT";
    }

    @GetMapping("/user/test")
    public String userEndPoint() {
        // fetching current request authentication details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal()); // will return the principal set to security context
        System.out.println(authentication.getName()); // will return the user's username/email
        System.out.println(authentication.getCredentials()); // will return user's credential if set
        System.out.println(authentication.getAuthorities()); // will return user's roles/permissions
        return "USER ENDPOINT";
    }
}
