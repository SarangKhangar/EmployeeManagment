package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.entity.Employee;
import com.example.demo.globalexceptionhandler.EmployeeNotFoundException;
import com.example.demo.repo.EmployeeRepo;

@Service
public class EmployeeServieImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepo repo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public List<EmployeeDTO> getAllEmployee() {
		List<Employee> employeeList = repo.findAll();
		List<EmployeeDTO> dtoList = new ArrayList<>();
		dtoList = employeeList.stream()
		        .map(e -> mapper.map(e, EmployeeDTO.class))
		        .collect(Collectors.toList());
//		for(Employee emp : employeeList) {
//			dtoList.add(mapper.map(emp, EmployeeDTO.class));
//		}
		return dtoList;
	}

	@Override
	public EmployeeDTO createEmployee(EmployeeDTO dto) {
		Employee employee = mapper.map(dto, Employee.class);
		employee.setPassword(encoder.encode(dto.getPassword()));
		this.repo.save(employee);
		EmployeeDTO empdto = mapper.map(employee, EmployeeDTO.class);
		return empdto;
	}

	@Override
	public EmployeeDTO updateEmployee(EmployeeDTO dto, Long id) throws EmployeeNotFoundException {
		Optional<Employee> optn = repo.findById(dto.getId());
		Employee emp = new Employee();
		if(optn.isPresent()) {
		    emp = this.mapper.map(dto, Employee.class);
			emp.setPassword(encoder.encode(dto.getPassword()));
		     this.repo.save(emp);
		     return this.mapper.map(emp, EmployeeDTO.class);
		}
		else {
			throw new EmployeeNotFoundException("Employee not found");
		}
		
	}

	@Override
	public boolean deleteEpmloyee(Long id) throws EmployeeNotFoundException {
		Optional<Employee> optn = this.repo.findById(id);
		if(optn.isPresent()) {
			this.repo.deleteById(id);
			return true;
		}
		else {
			throw new EmployeeNotFoundException("Employee not found");
		}
		
	}

	@Override
	public EmployeeDTO patchingEmployee(EmployeeDTO dto, Long id) throws EmployeeNotFoundException{
		Employee emp = this.repo.findById(id).orElseThrow(()->new EmployeeNotFoundException(" Employee not found"));
		Optional.ofNullable(dto.getFname()).ifPresent(emp::setFname);
		Optional.ofNullable(dto.getLname()).ifPresent(emp::setLname);
		Optional.ofNullable(dto.getDept()).ifPresent(emp::setDept);
		Optional.ofNullable(dto.getAge()).ifPresent(emp::setAge);
		Optional.ofNullable(dto.getUsername()).ifPresent(emp::setUsername);
		Optional.ofNullable(dto.getRole()).ifPresent(emp::setRole);
		if(dto.getPassword() != null) {
			emp.setPassword(encoder.encode(dto.getPassword()));
		}
		
		this.repo.save(emp);
		return this.mapper.map(emp, EmployeeDTO.class);
	}

	@Override
	public EmployeeDTO getEmployeeById (Long id) throws EmployeeNotFoundException{
		Optional<Employee> optn = this.repo.findById(id);
		Employee emp = new Employee();
		if(optn.isPresent()) {
			emp = optn.get();
			return this.mapper.map(this.repo.save(emp), EmployeeDTO.class);
		}
		else {
			throw new EmployeeNotFoundException("No employee with this id");
		}
	}

}
