package com.retailcloud.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailcloud.dto.CreateEmployeeRequest;
import com.retailcloud.model.Department;
import com.retailcloud.model.Employee;
import com.retailcloud.model.RoleTitle;
import com.retailcloud.repository.DepartmentRepository;
import com.retailcloud.repository.EmployeeRepository;
import com.retailcloud.repository.RoleTitleRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private RoleTitleRepository roleTitleRepository;

	public Employee createEmployee(CreateEmployeeRequest request) {
		Department dept = departmentRepository.findById(request.departmentId)
				.orElseThrow(() -> new RuntimeException("Department not found"));

		RoleTitle role = roleTitleRepository.findById(request.roleId)
				.orElseThrow(() -> new RuntimeException("Role not found"));

		Employee manager = request.reportingManagerId != null ? employeeRepository.findById(request.reportingManagerId)
				.orElseThrow(() -> new RuntimeException("Reporting manager not found")) : null;

		Employee emp = new Employee();
		emp.setName(request.name);
		emp.setDob(request.dob);
		emp.setSalary(request.salary);
		emp.setAddress(request.address);
		emp.setJoiningDate(request.joiningDate);
		emp.setDepartment(dept);
		emp.setRole(role);
		emp.setReportingManager(manager);
		validateEmployee(emp);
		return employeeRepository.save(emp);
	}

	public Employee updateEmployee(Employee employee) {
		validateEmployee(employee);
		Employee employeeData = employeeRepository.findById(employee.getId())
				.orElseThrow(() -> new RuntimeException("Employee not found"));
		Employee savedData = null;
		if (employeeData != null) {
			savedData = employeeRepository.save(employee);
		}
		return savedData;
	}
	
	public void validateEmployee(Employee employee) {
	    if (employee.getName() == null || employee.getName().trim().isEmpty()) {
	        throw new IllegalArgumentException("Employee name is required");
	    }

	    if (employee.getDob() == null || employee.getDob().isAfter(LocalDate.now())) {
	        throw new IllegalArgumentException("Date of birth must be a valid past date");
	    }

	    if (employee.getSalary() == null || employee.getSalary() < 0) {
	        throw new IllegalArgumentException("Salary must be a non-negative number");
	    }

	    if (employee.getJoiningDate() == null || employee.getJoiningDate().isAfter(LocalDate.now())) {
	        throw new IllegalArgumentException("Joining date must be a valid past or current date");
	    }

	    if (employee.getRole() == null) {
	        throw new IllegalArgumentException("Role is required");
	    }

	    if (employee.getDepartment() == null) {
	        throw new IllegalArgumentException("Department is required");
	    }

	    if (employee.getReportingManager() == null && employee.getId() != null) {
	        throw new IllegalArgumentException("Reporting manager is required");
	    }

	    if (employee.getReportingManager() != null && employee.getReportingManager().getId().equals(employee.getId())) {
	        throw new IllegalArgumentException("Employee cannot report to themselves");
	    }
	}
	
	public Employee updateEmployeeDepartment(Long employeeId, Long departmentId) {
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new RuntimeException("Employee not found"));

        Department newDepartment = departmentRepository.findById(departmentId)
            .orElseThrow(() -> new RuntimeException("Department not found"));

        employee.setDepartment(newDepartment);
        return employeeRepository.save(employee);
    }

}
