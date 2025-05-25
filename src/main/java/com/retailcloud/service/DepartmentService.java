package com.retailcloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailcloud.model.Department;
import com.retailcloud.model.Employee;
import com.retailcloud.repository.DepartmentRepository;
import com.retailcloud.repository.EmployeeRepository;

@Service
public class DepartmentService {
	
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
    private EmployeeRepository employeeRepository;

	public Department addDepartment(Department department) {
		
        if (department.getName() == null || department.getName().isBlank()) {
            throw new IllegalArgumentException("Department name is required");
        }

        if (departmentRepository.existsByName(department.getName())) {
            throw new IllegalArgumentException("Department already exists");
        }

        return departmentRepository.save(department);
    }
	
	public Department updateDepartment(Department department) {
        Department existingDepartment = departmentRepository.findById(department.getId())
            .orElseThrow(() -> new RuntimeException("Department not found with id " + department.getId()));

        existingDepartment.setName(department.getName());

        if (department.getDepartmentHead() != null && department.getDepartmentHead().getId() != null) {
            Employee head = employeeRepository.findById(department.getDepartmentHead().getId())
                .orElseThrow(() -> new RuntimeException("Department Head not found with id " + department.getDepartmentHead().getId()));
            existingDepartment.setDepartmentHead(head);
        }

        return departmentRepository.save(existingDepartment);
    }
	
	public void softDeleteDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id " + departmentId));

        if (department.getEmployees() != null && !department.getEmployees().isEmpty()) {
            throw new RuntimeException("Cannot delete department. Employees are assigned to this department.");
        }

        department.setDeleted(true);
        departmentRepository.save(department);
    }
}
