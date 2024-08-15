package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserProfileBean;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth") 
public class UserController {
	
	  @GetMapping("/welcome") 
	    public String welcome() { 
	        return "Welcome this endpoint is not secure"; 
	    } 
	  
	    @GetMapping("/user/userProfile") 
	    @PreAuthorize("hasAuthority('ROLE_USER')") 
	    public String userProfile() { 
	        return "Welcome to User Profile"; 
	    } 
	  
	    @GetMapping("/admin/adminProfile") 
	    @PreAuthorize("hasAuthority('ROLE_ADMIN')") 
	    public String adminProfile() { 
	        return "Welcome to Admin Profile"; 
	    } 
	    
	    @PostMapping(path = "/user-profiles", produces = "application/json")
	    public ResponseEntity<String> create(@Valid @RequestBody UserProfileBean userProfile) {
	        //custom process
	        return new ResponseEntity<>("created", HttpStatus.CREATED);
	    }

}
