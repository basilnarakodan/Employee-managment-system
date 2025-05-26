package com.retailcloud.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Employee extends AuditLog{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
    private LocalDate dob;
    private Double salary;
    private String address;
    public Double bonusPercentage;
    private LocalDate joiningDate;

    @ManyToOne
    private Department department;

    @ManyToOne
    private Employee reportingManager;
    
    @ManyToOne
    private RoleTitle role;

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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Employee getReportingManager() {
		return reportingManager;
	}

	public void setReportingManager(Employee reportingManager) {
		this.reportingManager = reportingManager;
	}

	public RoleTitle getRole() {
		return role;
	}

	public void setRole(RoleTitle role) {
		this.role = role;
	}

	public Double getBonusPercentage() {
		return bonusPercentage;
	}

	public void setBonusPercentage(Double bonusPercentage) {
		this.bonusPercentage = bonusPercentage;
	}

}
