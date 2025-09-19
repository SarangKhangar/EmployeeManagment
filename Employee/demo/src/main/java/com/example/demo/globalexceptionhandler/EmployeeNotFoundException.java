package com.example.demo.globalexceptionhandler;

public class EmployeeNotFoundException extends RuntimeException {

	public EmployeeNotFoundException(String message) {
        super(message);
    }
	
}
