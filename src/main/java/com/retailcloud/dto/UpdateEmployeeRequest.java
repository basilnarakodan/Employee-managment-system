package com.retailcloud.dto;

import java.time.LocalDate;

public class UpdateEmployeeRequest {
    public String name;
    public LocalDate dob;
    public Double salary;
    public String address;
    public LocalDate joiningDate;
    public Double bonusPercentage;
    public Long departmentId;
    public Long roleId;
    public Long reportingManagerId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public LocalDate getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}
	public Double getBonusPercentage() {
		return bonusPercentage;
	}
	public void setBonusPercentage(Double bonusPercentage) {
		this.bonusPercentage = bonusPercentage;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getReportingManagerId() {
		return reportingManagerId;
	}
	public void setReportingManagerId(Long reportingManagerId) {
		this.reportingManagerId = reportingManagerId;
	}
    
    
}
