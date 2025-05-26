package com.retailcloud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retailcloud.dto.CreateEmployeeRequest;
import com.retailcloud.dto.EmployeeDetailsDto;
import com.retailcloud.dto.EmployeeLookup;
import com.retailcloud.dto.UpdateEmployeeRequest;
import com.retailcloud.model.Employee;
import com.retailcloud.repository.EmployeeRepository;
import com.retailcloud.service.EmployeeService;

@RestController
@RequestMapping("/v1/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private EmployeeRepository employeeRepository;

	@PostMapping
	public ResponseEntity<?> createEmployee(@RequestBody CreateEmployeeRequest request) {
		Employee created = employeeService.createEmployee(request);
		Map<String, String> response = new HashMap<>();
		response.put("id", created.getId().toString());
		response.put("message", "Employee created successfully");
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody UpdateEmployeeRequest request) {
		Employee updated = employeeService.updateEmployee(id, request);
		Map<String, String> response = new HashMap<>();
		response.put("id", updated.getId().toString());
		response.put("message", "Employee details updated successfully");
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{employeeId}/department/{departmentId}")
	public ResponseEntity<?> moveEmployeeToDepartment(@PathVariable Long employeeId, @PathVariable Long departmentId) {

		Employee updatedEmployee = employeeService.updateEmployeeDepartment(employeeId, departmentId);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Employee moved to new department successfully.");
		return ResponseEntity.ok(response);
	}

	@GetMapping("/details")
	public ResponseEntity<Page<EmployeeDetailsDto>> getAllEmployees(@PageableDefault(size = 20) Pageable pageable) {
		Page<EmployeeDetailsDto> employees = employeeRepository.findAllEmployeeDetails(pageable);
		return ResponseEntity.ok(employees);
	}

	@GetMapping()
	public ResponseEntity<?> getEmployees(@RequestParam(required = false) Boolean lookup,
			@PageableDefault(size = 20) Pageable pageable) {
		if (Boolean.TRUE.equals(lookup)) {
			Page<EmployeeLookup> lookups = employeeRepository.findAllEmployeeLookups(pageable);
			return ResponseEntity.ok(lookups);
		} else {
			Page<EmployeeDetailsDto> employees = employeeRepository.findAllEmployeeDetails(pageable);
			return ResponseEntity.ok(employees);
		}
	}

}
