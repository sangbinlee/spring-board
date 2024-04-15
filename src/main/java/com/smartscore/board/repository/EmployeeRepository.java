package com.smartscore.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartscore.board.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}