package com.retailcloud.dto;

public class EmployeeDetailsDto {
    private Long id;
    private String name;
    private String role;
    private String departmentName;
    private String reportingManagerName;

    public EmployeeDetailsDto(Long id, String name, String role, String departmentName, String reportingManagerName) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.departmentName = departmentName;
        this.reportingManagerName = reportingManagerName;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getReportingManagerName() {
		return reportingManagerName;
	}

	public void setReportingManagerName(String reportingManagerName) {
		this.reportingManagerName = reportingManagerName;
	}

}
