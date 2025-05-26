package com.retailcloud.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailcloud.dto.CreateEmployeeRequest;
import com.retailcloud.dto.UpdateEmployeeRequest;
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


	public Employee updateEmployee(Long id, UpdateEmployeeRequest request) {
		Employee emp = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));

		if (request.name != null)
			emp.setName(request.name);
		if (request.dob != null)
			emp.setDob(request.dob);
		if (request.salary != null)
			emp.setSalary(request.salary);
		if (request.address != null)
			emp.setAddress(request.address);
		if (request.joiningDate != null)
			emp.setJoiningDate(request.joiningDate);
		if (request.bonusPercentage != null)
			emp.setBonusPercentage(request.bonusPercentage);

		if (request.departmentId != null) {
			Department department = departmentRepository.findById(request.departmentId)
					.orElseThrow(() -> new RuntimeException("Department not found"));
			emp.setDepartment(department);
		}

		if (request.roleId != null) {
			RoleTitle role = roleTitleRepository.findById(request.roleId)
					.orElseThrow(() -> new RuntimeException("Role not found"));
			emp.setRole(role);
		}

		if (request.reportingManagerId != null) {
			Employee manager = employeeRepository.findById(request.reportingManagerId)
					.orElseThrow(() -> new RuntimeException("Reporting Manager not found"));
			emp.setReportingManager(manager);
		}

		return employeeRepository.save(emp);
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

		if(newDepartment.isDeleted()) {
			throw new IllegalArgumentException("Department not found");
		}
		employee.setDepartment(newDepartment);
		return employeeRepository.save(employee);
	}

}
