package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
	
	Employee findById(long id);
	
	Optional<Employee> findByName(String name);

	List<Employee> findByDeleteFlgFalse();
	List<Employee> findByDeleteFlgTrue();
	
    List<Employee> findByNameContainingIgnoreCase(String keyword);
	List<Employee> findByNameContaining(String keyword);

}
