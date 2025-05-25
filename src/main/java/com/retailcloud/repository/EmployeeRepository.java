package com.retailcloud.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.retailcloud.dto.EmployeeLookup;
import com.retailcloud.model.Employee;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query("SELECT new com.retailcloud.dto.EmployeeLookup(e.id, e.name) FROM Employee e")
	Page<EmployeeLookup> findAllEmployeeLookups(Pageable pageable);

}
