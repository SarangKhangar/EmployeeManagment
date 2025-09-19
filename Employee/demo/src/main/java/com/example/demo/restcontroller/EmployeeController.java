package com.example.demo.restcontroller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.globalexceptionhandler.EmployeeNotFoundException;
import com.example.demo.service.EmployeeService;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeService service;
	
	
	@GetMapping("/emp")
	public ResponseEntity<List<EmployeeDTO>> getEmployees(){
		return new ResponseEntity<>(this.service.getAllEmployee(), HttpStatus.OK);
	}
	
	@GetMapping("/emp/{id}")
	public ResponseEntity<EmployeeDTO> getEmp(@PathVariable Long id) throws EmployeeNotFoundException{
		return new ResponseEntity<>(this.service.getEmployeeById(id), HttpStatus.FOUND);
	}
	
	@PostMapping("/sign-up")
	public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody EmployeeDTO dto){
		return new ResponseEntity<>(this.service.createEmployee(dto), HttpStatus.CREATED);
	}
	
	@PutMapping("/emp/{id}")
	public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO dto, @PathVariable Long id) throws EmployeeNotFoundException{
		return new ResponseEntity<>(this.service.updateEmployee(dto, id), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delet/{id}")
	public ResponseEntity<Void> removeEmpoyee(@PathVariable Long id) throws EmployeeNotFoundException {
		boolean deleted = this.service.deleteEpmloyee(id);
		if(deleted) {
			return ResponseEntity.noContent().build();	
		}
		else {
			throw new EmployeeNotFoundException("No Suck employee in system");
		}
	}
	
	@PatchMapping("/emp/{id}")
	public ResponseEntity<EmployeeDTO> patchingEmp(@RequestBody EmployeeDTO dto, @PathVariable Long id) throws Exception{
		return new ResponseEntity<>(this.service.patchingEmployee(dto, id), HttpStatus.ACCEPTED);
	}

}