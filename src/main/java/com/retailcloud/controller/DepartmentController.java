package com.retailcloud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retailcloud.dto.DepartmentDetailsDto;
import com.retailcloud.dto.EmployeeLookup;
import com.retailcloud.model.Department;
import com.retailcloud.repository.DepartmentRepository;
import com.retailcloud.service.DepartmentService;

@RestController
@RequestMapping("v1/departments")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private DepartmentRepository departmentRepository;

	@PostMapping
	public ResponseEntity<?> createDepartment(@RequestBody Department department) {
		Department savedDepartment = departmentService.addDepartment(department);
		Map<String, String> response = new HashMap<>();
		response.put("id", savedDepartment.getId().toString());
		response.put("message", "Department created successfully");
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateDepartment(@PathVariable Long id, @RequestBody Department department) {

		if (!id.equals(department.getId())) {
			return ResponseEntity.badRequest().build();
		}
		Department updated = departmentService.updateDepartment(department);
		Map<String, String> response = new HashMap<>();
		response.put("id", updated.getId().toString());
		response.put("message", "Department details updated successfully");
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteDepartment(@PathVariable Long id) {
		try {
			departmentService.softDeleteDepartment(id);
			Map<String, String> response = new HashMap<>();
			response.put("message", "Deleted successfully");
			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
			Map<String, String> error = new HashMap<>();
			error.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(error);
		}
	}

	@GetMapping
	public ResponseEntity<Page<DepartmentDetailsDto>> getAllDepartments(@PageableDefault(size = 20) Pageable pageable) {
		Page<DepartmentDetailsDto> departments = departmentRepository.findAllDepartmentDetails(pageable);
		return ResponseEntity.ok(departments);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getDepartmentById(@PathVariable Long id, @RequestParam(required = false) String expand) {

		if ("employee".equalsIgnoreCase(expand)) {
			Department department = departmentRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Department not found"));
			List<EmployeeLookup> employeeList=new ArrayList<>();
			department.getEmployees().forEach(emp->{
				EmployeeLookup empDetails=new EmployeeLookup();
				empDetails.setId(emp.getId());
				empDetails.setName(emp.getName());
				employeeList.add(empDetails);
			});
			DepartmentDetailsDto res=new DepartmentDetailsDto();
			res.setId(department.getId());
			res.setName(department.getName());
			res.setDepartmentHeadName(department.getDepartmentHead()!=null?department.getDepartmentHead().getName():null);
			res.setEmployeeCount( (long) employeeList.size());
			res.setEmployees(employeeList);
			return ResponseEntity.ok(res);
		} else {
			DepartmentDetailsDto department = departmentRepository.findDepartmentDetailsById(id)
			.orElseThrow(() -> new RuntimeException("Department not found"));
			return ResponseEntity.ok(department);
		}
	}
}
