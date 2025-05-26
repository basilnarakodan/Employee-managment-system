package com.retailcloud.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.retailcloud.dto.DepartmentDetailsDto;
import com.retailcloud.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
	
    boolean existsByName(String name);
    
    
    @Query("""
    	    SELECT new com.retailcloud.dto.DepartmentDetailsDto(
    	        d.id,
    	        d.name,
    	        COALESCE(h.name, 'N/A'),
    	        COUNT(e.id)
    	    )
    	    FROM Department d
    	    LEFT JOIN d.departmentHead h
    	    LEFT JOIN d.employees e
    	    WHERE d.deleted = false
    	    GROUP BY d.id, d.name, h.name
    	""")
    	Page<DepartmentDetailsDto> findAllDepartmentDetails(Pageable pageable);

    
    @Query("""
    	    SELECT new com.retailcloud.dto.DepartmentDetailsDto(
    	        d.id,
    	        d.name,
    	        COALESCE(h.name, 'N/A'),
    	        COUNT(e.id)
    	    )
    	    FROM Department d
    	    LEFT JOIN d.departmentHead h
    	    LEFT JOIN d.employees e
    	    WHERE d.id = :id AND d.deleted = false
    	    GROUP BY d.id, d.name, h.name
    	""")
    	Optional<DepartmentDetailsDto> findDepartmentDetailsById(@Param("id") Long id);

}
