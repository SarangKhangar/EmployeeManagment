package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.globalexceptionhandler.EmployeeNotFoundException;

@Service
public interface EmployeeService {
	
	List<EmployeeDTO> getAllEmployee();
	
	EmployeeDTO getEmployeeById(Long id) throws EmployeeNotFoundException;

	EmployeeDTO createEmployee(EmployeeDTO dto);

	EmployeeDTO updateEmployee(EmployeeDTO dto, Long id) throws EmployeeNotFoundException;

	boolean deleteEpmloyee(Long id) throws EmployeeNotFoundException;
	
	EmployeeDTO patchingEmployee(EmployeeDTO dto, Long id) throws EmployeeNotFoundException;

}
