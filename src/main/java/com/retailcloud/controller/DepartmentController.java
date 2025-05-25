package com.retailcloud.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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

import com.retailcloud.model.Department;
import com.retailcloud.repository.DepartmentRepository;
import com.retailcloud.service.DepartmentService;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private DepartmentRepository departmentRepository;

	@PostMapping
	public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
		Department savedDepartment = departmentService.addDepartment(department);
		return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department department) {

		if (!id.equals(department.getId())) {
			return ResponseEntity.badRequest().build();
		}

		Department updated = departmentService.updateDepartment(department);
		return ResponseEntity.ok(updated);
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
	public ResponseEntity<Page<Department>> getAllDepartments(@PageableDefault(size = 20) Pageable pageable) {
		Page<Department> departments = departmentRepository.findAll(pageable);
		return ResponseEntity.ok(departments);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getDepartmentById(@PathVariable Long id, @RequestParam(required = false) String expand) {

		Department department = departmentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Department not found"));

		if ("employee".equalsIgnoreCase(expand)) {
			return ResponseEntity.ok(department);
		} else {
			Department departmentWithoutEmployee = new Department();
			departmentWithoutEmployee.setId(department.getId());
			departmentWithoutEmployee.setName(department.getName());
			departmentWithoutEmployee.setDepartmentHead(department.getDepartmentHead());
			return ResponseEntity.ok(departmentWithoutEmployee);
		}
	}
}
