package com.retailcloud.dto;

import java.util.List;

public class DepartmentDetailsDto {
	private Long id;
	private String name;
	private String departmentHeadName;
	private Long employeeCount;
	private List<EmployeeLookup> employees;

	public DepartmentDetailsDto() {
		super();
	}

	public DepartmentDetailsDto(Long id, String name, String departmentHeadName, Long employeeCount) {
		this.id = id;
		this.name = name;
		this.departmentHeadName = departmentHeadName;
		this.employeeCount = employeeCount;
	}

	public DepartmentDetailsDto(Long id, String name, String departmentHeadName, Long employeeCount,
			List<EmployeeLookup> employees) {
		this.id = id;
		this.name = name;
		this.departmentHeadName = departmentHeadName;
		this.employeeCount = employeeCount;
		this.employees = employees;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartmentHeadName() {
		return departmentHeadName;
	}

	public void setDepartmentHeadName(String departmentHeadName) {
		this.departmentHeadName = departmentHeadName;
	}

	public Long getEmployeeCount() {
		return employeeCount;
	}

	public void setEmployeeCount(Long employeeCount) {
		this.employeeCount = employeeCount;
	}

	public List<EmployeeLookup> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeLookup> employees) {
		this.employees = employees;
	}
	
	

}
