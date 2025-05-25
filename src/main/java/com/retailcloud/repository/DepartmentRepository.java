package com.retailcloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retailcloud.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
	
    boolean existsByName(String name);

}
