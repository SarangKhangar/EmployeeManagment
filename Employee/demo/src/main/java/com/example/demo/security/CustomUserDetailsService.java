package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Employee;
import com.example.demo.repo.EmployeeRepo;

@Component
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private EmployeeRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 Employee emp = repo.findByUsername(username)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

	        return User.builder()
	                .username(emp.getUsername())
	                .password(emp.getPassword())
	                .roles(emp.getRole())
	                .build();
	}

}
