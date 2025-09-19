package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
		@Autowired
	    private AuthenticationManager authManager;
		
		@Autowired
	    private CustomUserDetailsService userDetailsService;
		
		@Autowired
	    private JwtUtil jwtUtil;

	    @PostMapping("/login")
	    public String login(@RequestBody AuthRequest request) {
	        authManager.authenticate(
	                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
	        );
	        UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
	        return jwtUtil.generateToken(user.getUsername());
	    }
	    
	    

}
